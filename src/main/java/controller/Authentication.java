package controller;

import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;

import model.Encode;
import model.User;

public class Authentication {
	
	private static Authentication AUTH = null;
	private static User USER = null;
	
	private Authentication() {
	}
	
	public Authentication getInstance() {
		if (AUTH == null) {
			AUTH = new Authentication();
		}
		return AUTH;
	}
	
	public User getUser() {
		return Authentication.USER;
	}
	
	private void setUser(User user) {
		Authentication.USER = user;
	}
	
	public Boolean authenticate(String userID, String psw){
		
		//TODO
		//if psw is not encoded yet
		try {
			psw = Encode.cryptingString(psw);
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(dao.UserDAO.isUserInDatabase(userID)) {//Account found
			
			//TODO
			//UnknownHostException because in the user there is the cart
			//and in the cart there are products with the IP address of who added it
			try {
				setUser(dao.UserDAO.getUser(userID, psw)); //Attempting to match psw
				//if the user can login, it's set in USER;
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(Authentication.USER != null) { //if user is authenticated, do login
				//TODO
				System.out.println("Can login.");
				return true;
			}
			else {
				//TODO
				System.out.println("Password not correct.");
			}
		}
		else {
			//TODO
			System.out.println("User not found.");
		}
		
		return false;
		
	}

}
