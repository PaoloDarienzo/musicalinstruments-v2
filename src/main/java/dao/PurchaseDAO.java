package dao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.DeliveryPoint;
import model.MetodoDiConsegna;
import model.Payment;
import model.ProductInCart;
import model.Purchase;

/**
 * PurchaseDAO class contains all the methods that interact with the database relatively to 
 * the purchase and to the classes that extend it.
 * @author Paolo D'Arienzo
 * @version 1.6
 */
public class PurchaseDAO {
	
	private final static String JDBC_URL = "jdbc:postgresql://localhost:5432/strumenti_database";
    private final static String JDBC_USERNAME = "postgres";
    private final static String JDBC_PASSWORD = "password";
    
    private final static String NOME_TABELLA = "ordine";
	
    /**
     * Adds the purchase in the database
     * @param acquisto the purchase to add
     */
	public static void addInDatabase(Purchase acquisto){
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			//if an error occurs with the connection to the database driver
			e.printStackTrace();
		}
		
		try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)){
			
			for (ProductInCart prodottoAggiunto : acquisto.getListOfProductsPurchased()) {
				
    		
	    		//insertion in "aggiunto"
	    		try (PreparedStatement pst = con.prepareStatement(
	    				"INSERT INTO " + NOME_TABELLA
	    				+ "(id, idstrumento, npezzi, ora, data, ipacquirente, metododiconsegna, "
	    				+ "prezzototale, carrelloutente, via, civico, cap, "
	    				+ "nomemetodo, credenziali) "
	    				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
	    			
	    			pst.setObject(1, acquisto.getIDPurchase());
	    			pst.setObject(2, prodottoAggiunto.getProduct().getID());
	    			pst.setInt(3, prodottoAggiunto.getNumeroProdotto());
	    			pst.setTime(4, acquisto.getPurchaseTime());
	    			pst.setDate(5, acquisto.getPurchaseDate());
	    			pst.setString(6, acquisto.getPurchaseIP().toString());
	    			pst.setString(7, acquisto.getMetodoDiConsegnaFromPurchase().toString());
	    			pst.setFloat(8, acquisto.getFinalPriceFromPurchase());
	    			pst.setString(9, acquisto.getUserMailFromPurchase());;
	    			pst.setString(10, acquisto.getPuntoDiConsegnaFromPurchase().getVia());
	    			pst.setString(11, acquisto.getPuntoDiConsegnaFromPurchase().getCivico());
	    			pst.setString(12, acquisto.getPuntoDiConsegnaFromPurchase().getCAP());
	    			pst.setString(13, acquisto.getPaymentFromPurchase().getNomeMetodo());
	    			pst.setString(14, acquisto.getPaymentFromPurchase().getCredenziali());
	    			
	    			int n = pst.executeUpdate();
	    			System.out.println("Inserite " + n + " righe in tabella ordine: "
	    			+ acquisto.getUserMailFromPurchase() + ", " + acquisto.getIDPurchase() + ".");
	    			
	    		} catch (SQLException e) {
	    			System.out.println("Errore durante inserimento dati: " + e.getMessage());
	    		}
    		
			}
    		
    	} catch (SQLException e){
    		System.out.println("Problema durante la connessione iniziale alla base di dati: " + e.getMessage());
    	}
		
	}
	
	/**
	 * Returns a Purchase object with the indicated ID
	 * @param purchaseID is the ID of the purchase to search
	 * @return the purchase with the ID indicated
	 * @throws UnknownHostException if an error occurs with the determination of the IP address
	 */
	public static Purchase getFromDatabase(UUID purchaseID) throws UnknownHostException {
    	
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			//if an error occurs with the connection to the database driver
			e.printStackTrace();
		}
    	
    	try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)){

    		try (PreparedStatement pst = con.prepareStatement(
    				"SELECT * FROM " + NOME_TABELLA + " "
    				+ "WHERE id = ?")) {
    			
    			pst.setObject(1, purchaseID);
    			
    			ResultSet rs = pst.executeQuery();
    			
    			while (rs.next()) {

    				Time ora = rs.getTime("ora");
    				Date data = rs.getDate("data");
    				String indirizzoIP = rs.getString("ipacquirente");
    				MetodoDiConsegna metodo = MetodoDiConsegna.valueOf(rs.getString("metododiconsegna"));
    				float prezzoTotale = rs.getFloat("prezzototale");
    				String proprietarioCarrello = rs.getString("carrelloutente");
    				
    				Payment pagamentoUsato = new Payment(proprietarioCarrello,
    											rs.getString("nomemetodo"), rs.getString("credenziali"));
    				
    				DeliveryPoint puntoUsato = new DeliveryPoint(proprietarioCarrello,
    											rs.getString("citta"), rs.getString("via"),
    											rs.getString("civico"), rs.getString("cap"));
    				
    				InetAddress ipAcquirente = InetAddress.getByName(indirizzoIP);
    				
    				List<ProductInCart> articoliAcquistati = new ArrayList<ProductInCart>();
    				
    				return new Purchase(data, ora, proprietarioCarrello, purchaseID, ipAcquirente, metodo,
    						pagamentoUsato, puntoUsato, prezzoTotale, articoliAcquistati);
    				 
    			}
    			
    		} catch (SQLException e) {
    			System.out.println("Errore durante query dei dati: " + e.getMessage());
    		}
    		
    	} catch (SQLException e){
    		System.out.println("Problema durante la connessione iniziale alla base di dati: " + e.getMessage());
    	}
    	
		return null;
    	
    }

}
