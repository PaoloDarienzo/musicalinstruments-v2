package model;

import dao.DeliveryPointDAO;

/**
 * DeliveryPoint class represents a delivery point for the user.
 * @author Paolo D'Arienzo
 * @version 1.5
 */
public class DeliveryPoint {
    
	private final String userMail;
    private final String citta;
    private final String via;
    private final String civico;
    private final String CAP;
    
    /**
     * Constructor of the delivery point.
     * @param mail is the mail of the user, which identify the owner of the delivery point
     * @param citta is the city of the delivery point
     * @param via is the address of the delivery point
     * @param civico is the house number of the delivery point
     * @param CAP is the postal code of the delivery point
     */
    public DeliveryPoint(String mail, String citta, String via, String civico, String CAP){
    	this.userMail = mail;
        this.citta = citta;
        this.via = via;
        this.civico = civico;
        this.CAP = CAP;
    }
    
    /**
     * Returns the mail of the user
     * @return the mail of the user
     */
    public String getUserMailFromDelPoint(){
    	return this.userMail;
    }

	/**
	 * Returns the city of the delivery point
	 * @return the city of the delivery point
	 */
	public String getCitta() {
		return this.citta;
	}
	
	/**
	 * Returns the address of the delivery point
	 * @return the address of the delivery point
	 */
	public String getVia() {
		return this.via;
	}
	
	/**
	 * Returns the house number of the delivery point
	 * @return the house number of the delivery point
	 */
	public String getCivico() {
		return this.civico;
	}
	
	/**
	 * Returns the postal code of the delivery point
	 * @return the postal code of the delivery point
	 */
	public String getCAP() {
		return this.CAP;
	}
	
	/**
	 * Composites the different fields of the delivery point and return it to string
	 * @return the complete address of the delivery point
	 */
	@Override
	public String toString() {
		return (via + " " + civico + ", " + CAP + ", " + citta + ".");
	}
	
    /**
     * Adds the delivery point in the database
     * @see DeliveryPointDAO#addOneDeliveryPoint(DeliveryPoint)
     */
    public void addInDatabase(){
    	DeliveryPointDAO.addOneDeliveryPoint(this);    	
    }
    
    /**
     * Deletes the delivery point in the database
     * @see DeliveryPointDAO#removeOneDeliveryPoint(DeliveryPoint)
     */
    public void deleteFromDatabase(){
    	DeliveryPointDAO.removeOneDeliveryPoint(this);    	
    }
    
}