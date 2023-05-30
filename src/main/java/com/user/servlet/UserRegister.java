package com.user.servlet;

import com.hash.hash;

// required for Md5 hashing.
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.UserDao;
import com.db.DBConnect;
import com.entity.User;

@WebServlet("/user_register")
public class UserRegister extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			String fullName = req.getParameter("fullname");
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			String repassword = req.getParameter("rpassword");
			long phnumber = Long.parseLong(req.getParameter("number"));
			
			String p=hash.getMd5(password);//hashed password.
			
			HttpSession session = req.getSession();
			// validating the password;
			if(password.equals(repassword)==false) {
				session.setAttribute("errorMsg2", "Passwords are not same !");
				resp.sendRedirect("signup.jsp");
				return;
				//System.out.println("Register Successfully");
				} 
			User u = new User(fullName, email, p, phnumber);
			UserDao dao = new UserDao(DBConnect.getConn());
			boolean f = dao.register(u);
		
		if (f) {
			session.setAttribute("successMsg", "Registered Successfully");
			resp.sendRedirect("signup.jsp");
			}
			else {
				session.setAttribute("errorMsg", "Something wrong on server");
				resp.sendRedirect("signup.jsp");
				//System.out.println("Something wrong on server");
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}