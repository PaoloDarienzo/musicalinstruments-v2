package controller;

import java.net.UnknownHostException;

import com.vaadin.ui.UI;

import model.Encode;
import model.User;

/**
 * The Authentication class ensure the authentication of the user.
 * @author Paolo D'Arienzo
 * @version 2.0
 */
public class Authentication {
	
	private User user = null;
	
	public Authentication() {
	}
	
	/**
	 * Returns the user
	 * @return the user
	 */
	public User getUser() {
		return this.user;
	}
	
	/**
	 * Sets the user
	 * @param user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * Authenticates the user if the credentials inserted are corrected
	 * @param userID is the ID of the user; it can be the e-mail or the username
	 * @param psw the password provided by the user
	 * @return true if the user is authenticated, false otherwise
	 */
	public Boolean authenticate(String userID, String psw){
		
		psw = Encode.cryptingString(psw);
		
		if(dao.UserDAO.isUserInDatabase(userID)) {//Account found
			
			//UnknownHostException because in the user there is the cart
			//and in the cart there are products with the IP address of who added it
			try {
				setUser(dao.UserDAO.getUser(userID, psw)); //Attempting to match psw
				//if the user can login, it's set in USER;
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(this.user != null) { //if user is authenticated, do login
				System.out.println("User: " + this.user.getMail() + " can login.");
				UI.getCurrent().getSession().setAttribute("AUTH", this);
				return true;
			}
			else {
				//Output supplied by loginView
				System.out.println("Password not correct.");
			}
		}
		else {
			//Output supplied by loginView
			System.out.println("User not found.");
		}
		
		return false;
		
	}
	
	/**
	 * Does the logout of the user from the authentication class, 
	 * i.e. sets to null the user and updates the value stored in the session
	 */
	public void doLogout() {
		System.out.println("User: " + this.user.getMail() + " has logged out.");
		this.user = null;
		UI.getCurrent().getSession().setAttribute("AUTH", this);
	}

}
