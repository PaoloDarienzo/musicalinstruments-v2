package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DeliveryPoint;


/**
 * DeliveryPointDAO class contains all the methods that interact with the database relatively to 
 * the delivery points and to the classes that extend it.
 * @author Paolo D'Arienzo
 * @version 1.6
 */
public class DeliveryPointDAO {
	
    private final static String JDBC_URL = "jdbc:postgresql://localhost:5432/strumenti_database";
    private final static String JDBC_USERNAME = "postgres";
    private final static String JDBC_PASSWORD = "effe";
    
    private final static String NOME_TABELLA = "puntodiconsegna";
	
    /**
     * Adds one delivery point to the user's list of delivery points in the database
     * @param puntoDiConsegna delivery point that has to be added
     */
	public static void addOneDeliveryPoint(DeliveryPoint puntoDiConsegna){
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			//if an error occurs with the connection to the database driver
			e.printStackTrace();
		}
    	
    	try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)){
    		
    		try (PreparedStatement pst = con.prepareStatement(
    				"INSERT INTO " + NOME_TABELLA + " "
    				+ "(cliente, via, civico, cap, citta) "
    				+ "VALUES (?,?,?,?,?)")) {
    			
    			pst.setString(1, puntoDiConsegna.getUserMailFromDelPoint());
    			pst.setString(2, puntoDiConsegna.getVia());
    			pst.setString(3, puntoDiConsegna.getCivico());
    			pst.setString(4, puntoDiConsegna.getCAP());
    			pst.setString(5, puntoDiConsegna.getCitta());
    			
    			int n = pst.executeUpdate();
    			System.out.println("Inserite " + n + " righe in tabella " 
    								+ NOME_TABELLA + ": " + puntoDiConsegna.getUserMailFromDelPoint() + ".");
    			
    		} catch (SQLException e) {
    			System.out.println("Errore durante inserimento dati: " + e.getMessage());
    		}
    		
    	} catch (SQLException e){
    		System.out.println("Problema durante la connessione iniziale alla base di dati: " + e.getMessage());
    	}
		
	}
	
	/**
	 * Removes one delivery point from the user's list of delivery points in the database
	 * @param puntoDiConsegna delivery point to remove
	 */
	public static void removeOneDeliveryPoint(DeliveryPoint puntoDiConsegna){
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			//if an error occurs with the connection to the database driver
			e.printStackTrace();
		}
    	
    	try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)){
    		
    		try (PreparedStatement pst = con.prepareStatement(
    				"DELETE FROM " + NOME_TABELLA + " "
    				+ "WHERE cliente = ? AND "
    				+ "via = ? AND "
    				+ "civico = ? AND "
    				+ "CAP = ?")) {
    			
    			pst.setString(1, puntoDiConsegna.getUserMailFromDelPoint());
    			pst.setString(2, puntoDiConsegna.getVia());
    			pst.setString(3, puntoDiConsegna.getCivico());
    			pst.setString(4, puntoDiConsegna.getCAP());
    			
    			int n = pst.executeUpdate();
    			System.out.println("Rimosse " + n + " righe da tabella " + NOME_TABELLA + ": " 
    								+ puntoDiConsegna.getUserMailFromDelPoint() + ".");
    			
    		} catch (SQLException e) {
    			System.out.println("Errore durante cancellazione dati: " + e.getMessage());
    		}
    		
    	} catch (SQLException e){
    		System.out.println("Problema durante la connessione iniziale alla base di dati: " + e.getMessage());
    	}
		
	}
	
	/**
	 * Queries the database in search of all the delivery points of the indicated user
	 * @param mailUtente indicates the user from which take the delivery points
	 * @return the ArrayList of delivery points of the user
	 */
	public static List<DeliveryPoint> getDeliveryPointsFromDatabase(String mailUtente){
		
		List<DeliveryPoint> puntiDiConsegnaAggiornati = new ArrayList<DeliveryPoint>();
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			//if an error occurs with the connection to the database driver
			e.printStackTrace();
		}
    	
    	try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)){
    		
    		try (PreparedStatement pst = con.prepareStatement(
    				"SELECT * FROM  " + NOME_TABELLA + " "
    				+ "WHERE cliente = ?")) {
    			
    			pst.setString(1, mailUtente);
    			
    			ResultSet rs = pst.executeQuery();
    			
    			while (rs.next()) {

    				String userMail = rs.getString("cliente");
    				String via = rs.getString("via");
    				String civico = rs.getString("civico");
    				String cap = rs.getString("cap");
    				String citta = rs.getString("citta");
    				
    				puntiDiConsegnaAggiornati.add(new DeliveryPoint(userMail, citta, via, civico, cap));

    			}
    			
    		} catch (SQLException e) {
    			System.out.println("Errore durante query dei dati: " + e.getMessage());
    		}
    		
    	} catch (SQLException e){
    		System.out.println("Problema durante la connessione iniziale alla base di dati: " + e.getMessage());
    	}
    	
    	return puntiDiConsegnaAggiornati;
		
	}

}