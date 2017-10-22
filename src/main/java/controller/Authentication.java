package controller;

import java.net.UnknownHostException;

import com.vaadin.ui.UI;

import model.Encode;
import model.User;

public class Authentication {
	
	private User user = null;
	
	public Authentication() {
	}
	
	public User getUser() {
		return this.user;
	}
	
	private void setUser(User user) {
		this.user = user;
	}
	
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
				System.out.println(this.toString());
				//VaadinSession.getCurrent().setAttribute("user", this.user);
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
	
	public void doLogout() {
		System.out.println("User: " + this.user.getMail() + " has logged out.");
		this.user = null;
		UI.getCurrent().getSession().setAttribute("AUTH", this);
	}

}
