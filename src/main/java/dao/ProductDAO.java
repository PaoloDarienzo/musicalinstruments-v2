package dao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import model.Brand;
import model.LivelloStrumento;
import model.Product;
import model.TipoStrumento;


/**
 * ProductDAO class contains all the methods that interact with the database relatively to 
 * the product and to the classes that extend it.
 * @author Paolo D'Arienzo
 * @version 1.6
 *
 */
public class ProductDAO {
	
    private final static String JDBC_URL = "jdbc:postgresql://localhost:5432/strumenti_database";
    private final static String JDBC_USERNAME = "postgres";
    private final static String JDBC_PASSWORD = "effe";
    
    /**
     * Adds the product in the database
     * @param prodotto is the product to add in the database
     */
    public static void addInDatabase(Product prodotto){
    	
    	try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			//if an error occurs with the connection to the database driver
			e.printStackTrace();
		}
    	
    	try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)){
    		
    		//insertion in "strumento"
    		try (PreparedStatement pst = con.prepareStatement(
    				"INSERT INTO strumento "
    				+ "(id, datainserimento, ipinserimento, nome, npezzidisponibili, "
    				+ "descrizione, peso, prezzo, classificazione, marca, producttype,"
    				+ "usato, sconto, npezziminimi, livelloconsigliato) "
    				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
    			
    			pst.setObject(1, prodotto.getID());
    			pst.setDate(2, prodotto.getDataInserimento());
    			pst.setString(3, prodotto.getIPInserimento().getHostAddress());
    			pst.setString(4, prodotto.getNome());
    			pst.setInt(5, prodotto.getNumeroPezziDisponibili());
    			pst.setString(6, prodotto.getDescrizione());
    			pst.setFloat(7, prodotto.getPeso());
    			pst.setFloat(8, prodotto.getPrezzo());
    			pst.setString(9, prodotto.getClassificazione().toString());
    			pst.setString(10, prodotto.getMarca().toString());
    			pst.setString(11, prodotto.getProductType());
    			pst.setBoolean(12, prodotto.isUsato());
    			pst.setInt(13, prodotto.getSconto());
    			pst.setInt(14, prodotto.getNumeroPezziMinimo());
    			pst.setString(15, prodotto.getLivelloConsigliato().toString());
    			
    			int n = pst.executeUpdate();
    			System.out.println("Inserite " + n + " righe in tabella strumento: "
    			+ prodotto.getNome() + ", " + prodotto.getID() +".");
    			
    		} catch (SQLException e) {
    			System.out.println("Errore durante inserimento dati: " + e.getMessage());
    		}
    		
    	} catch (SQLException e){
    		System.out.println("Problema durante la connessione iniziale alla base di dati: " + e.getMessage());
    	}
    	
    }
    
    /**
     * Updates the product in the database
     * @param prodotto is the product to update in the database
     */
    public static void updateInDatabase(Product prodotto){
    	
    	try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			//if an error occurs with the connection to the database driver
			e.printStackTrace();
		}
    	
    	try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)){
    		
    		try (PreparedStatement pst = con.prepareStatement(
    				"UPDATE strumento "
    				+ "SET nome = ?, npezzidisponibili = ?, descrizione = ?, peso = ?, prezzo = ?, "
    				+ "sconto = ?, npezziminimi = ?, livelloconsigliato = ?, usato = ? "
    				+ "WHERE id = ?")) {
    			
    			pst.setString(1, prodotto.getNome());
    			pst.setInt(2, prodotto.getNumeroPezziDisponibili());
    			pst.setString(3, prodotto.getDescrizione());
    			pst.setFloat(4, prodotto.getPeso());
    			pst.setFloat(5, prodotto.getPrezzo());
    			pst.setInt(6, prodotto.getSconto());
    			pst.setInt(7, prodotto.getNumeroPezziMinimo());
    			pst.setString(8, prodotto.getLivelloConsigliato().toString());
    			pst.setBoolean(9, prodotto.isUsato());
    			
    			pst.setObject(10, prodotto.getID());
    			
    			int n = pst.executeUpdate();
    			System.out.println("Modificate " + n + " righe in tabella strumento: " + prodotto.getID() + ".");
    			
    		} catch (SQLException e) {
    			System.out.println("Errore durante aggiornamento dati: " + e.getMessage());
    		}
    		
    	} catch (SQLException e){
    		System.out.println("Problema durante la connessione iniziale alla base di dati: " + e.getMessage());
    	}
    }
    
    /**
     * Removes the product from database;
     * if the product is an extended one, the reaction indicated in the database table
     * assures the deletion of the voice from all the slave tables
     * @param productID is the ID of the product that have to be deleted
     */
    public static void deleteFromDatabase(UUID productID){
    	
    	try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			//if an error occurs with the connection to the database driver
			e.printStackTrace();
		}
    	
    	try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)){
    		
    		try (Statement st = con.createStatement()){
    			st.executeUpdate(
    					"DELETE FROM strumento "
    					+ "WHERE id = '" + productID + "'");
    			
    			int n = st.getUpdateCount();
    			System.out.println("Rimosse " + n + " righe da tabella strumento: " + productID);
    			
    		} catch (SQLException e) {
    			System.out.println("Errore durante cancellazione dati: " + e.getMessage());
    		}
    		
    	} catch (SQLException e){
    		System.out.println("Problema durante la connessione iniziale alla base di dati: " + e.getMessage());
    	}
    	
    }
	
    /**
     * Returns a Product object with the indicated ID
     * @param productID is the ID of the product to get
     * @return the Product with the ID indicated
     * @throws UnknownHostException if an error occurs with the determination of the IP address
     */
    public static Product getFromDatabase(UUID productID) throws UnknownHostException {
    	
    	try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			//if an error occurs with the connection to the database driver
			e.printStackTrace();
		}
    	
    	try (Connection con = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)){

    		try (PreparedStatement pst = con.prepareStatement(
    				"SELECT * FROM strumento "
    				+ "WHERE id = ?")) {
    			
    			pst.setObject(1, productID);
    			
    			ResultSet rs = pst.executeQuery();
    			
    			while (rs.next()) {

    				Date datainserimento = rs.getDate("datainserimento");
    				String ipinserimento = rs.getString("ipinserimento");
    				String nome = rs.getString("nome");
    				int npezzidisponibili = rs.getInt("npezzidisponibili");
    				String descrizione = rs.getString("descrizione");
    				float peso = rs.getFloat("peso");
    				float prezzo = rs.getFloat("prezzo");
    				String classificazione = rs.getString("classificazione");
    				String marca = rs.getString("marca");
    				String productType = rs.getString("producttype");
    				int sconto = rs.getInt("sconto");
    				int nPezziMinimi = rs.getInt("npezziminimi");
    				String livelloConsigliato = rs.getString("livelloconsigliato");
    				Boolean usato = rs.getBoolean("usato");
    				
    				LivelloStrumento livelloEnum = LivelloStrumento.valueOf(livelloConsigliato);
    				TipoStrumento classificazioneEnum = TipoStrumento.valueOf(classificazione);
    				Brand marcaEnum = Brand.valueOf(marca);
    				
    				return new Product(productID, nome, npezzidisponibili, descrizione, peso, prezzo,
    						classificazioneEnum, marcaEnum, datainserimento, 
    						InetAddress.getByName(ipinserimento), productType,
    						sconto, nPezziMinimi, livelloEnum, usato);
    				 
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