package model;

import dao.PaymentDAO;

/**
 * Payment class represents the method of payment of the user.
 * @author Paolo D'Arienzo
 * @version 1.5
 */
public class Payment {
    
	private final String userMail;
    private final String nomeMetodo;
    private final String credenziali;

    /**
     * Constructor of class Payment.
     * @param mail is the mail of the user, which identify the owner of the payment method
     * @param nomeMetodo is the name of the payment method
     * @param credenziali are the credentials of the method
     */
    public Payment(String mail, String nomeMetodo, String credenziali){
        
    	this.userMail = mail;
        this.nomeMetodo = nomeMetodo;
        this.credenziali = credenziali;
        
    }
    
    /**
     * Returns the mail of the user
     * @return the mail of the user
     */
    public String getUserMailFromPayment(){
    	return this.userMail;
    }
    
    /**
     * Returns the name of the payment method
     * @return the name of the payment method
     */
    public String getNomeMetodo(){
        return this.nomeMetodo;
    }
    
    /**
     * Returns the credentials of the payment method
     * @return the credentials of the payment method
     */
    public String getCredenziali(){
        return this.credenziali;
    }
    
    /**
     * Returns the censored credentials of the payment method
     * @return the censored credentials of the payment method
     */
    public String getCredenzialiCensurate(){
    	int strLength = credenziali.length();
    	String firstPart = credenziali.substring(0, strLength - 3);
    	String lastPart = credenziali.substring(strLength - 3);
    	firstPart = firstPart.replaceAll("(?s).", "*");
        return (firstPart + lastPart);
    }
    
    /**
     * Returns the payment method with credentials censored
     * @return the payment fields, with credentials encrypted
     */
    @Override
    public String toString() {
    	return (nomeMetodo + ": " + this.getCredenzialiCensurate());
    }
    
    /**
     * Adds the payment in the database
     * @see PaymentDAO#addOnePayment(Payment)
     */
    public void addInDatabase(){
    	PaymentDAO.addOnePayment(this);    	
    }
    
    /**
     * Deletes the payment in the database
     * @see PaymentDAO#removeOnePayment(Payment)
     */
    public void deleteFromDatabase(){
    	PaymentDAO.removeOnePayment(this);    	
    }
    
}