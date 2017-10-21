package tests;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.*;

/**
 * This class initializes users and products, used for tests.
 * @author Paolo D'Arienzo
 * @version 1.5
 */
public class InitializationForTests {
	
	/**
	 * Initialize a list of users
	 * @return the list of initialized users
	 */
	protected static List<User> userInit(){
		
		List<User> userList = new ArrayList<User>();
		
		String pswEncrypted;
		
		pswEncrypted = Encode.cryptingString("Krifpol5878%lf!");
		
		User admin0 = new User("luciacrive995@gmail.com", "Lucia_crive", pswEncrypted,
				"Lucia", "Crivellini", "3909829182", "Verona",
				"LCLZCRV9484123", TipoCliente.ADMIN, "93980908890");
		userList.add(admin0);
		
		pswEncrypted = Encode.cryptingString("Criptami");

		User PC1 = new User("paolo.darienzo@studenti.univr.it", "paolodarienzo2", pswEncrypted,
				"Paolo", "D'Arienzo", "1234567890", "Verona", "DRNAPAMDWNO12F", TipoCliente.PROFESSIONISTA);
		userList.add(PC1);
		
		pswEncrypted = Encode.cryptingString("CriptamiTutto");
		
		User HC1 = new User("lorenzo.ricci@studenti.univr.it", "lorenzoricci2", 
				pswEncrypted, "Lorenzo", "Ricci", "1234567890", "Lugagnano", "ASDQO8N310L", TipoCliente.TITOLARE);
		userList.add(HC1);
		
		pswEncrypted = Encode.cryptingString("albero");
		
		User occasionale1 = new User("mario.rossi@gmail.com", "rossirossi", pswEncrypted, 
				"Mario", "Rossi", "0987654321", "Roma", "RSSMAR102FLAS");
		userList.add(occasionale1);
		
		return userList;
		
	}
	
	/**
	 * Initialize three products, one for each type
	 * @return the initialized list of products
	 * @throws UnknownHostException if an error occurs with the determination of the IP address
	 */
	protected static List<Product> productInit() throws UnknownHostException{
		
		List<Product> productList = new ArrayList<Product>();
		
		InetAddress AdminIP = InetAddress.getLocalHost();
		
		Product EKO_S300_SUNBURST = new Product(UUID.randomUUID(), "EKO S300 Sunburst", 27, 
				"La serie Starter nasce per offrire la possibilita' a tutti i chitarristi, "
				+ "in particolar modo i principianti, di avere uno strumento con caratteristiche simili a "
				+ "modelli di chitarra che hanno fatto la storia della musica.", 
				(float) 5.00, (float) 89.00, TipoStrumento.PIZZICO, Brand.EKO,
				new Date(2016-1900,11,02), AdminIP, "c", 0, 0, null, false);
		productList.add(EKO_S300_SUNBURST);
		
		Product ltd_alexi = new Product(UUID.randomUUID(), "ltd alexi", 2, 
				"Chitarra Elettrica Solid Body Signature Alexi Laiho. \n" + "Pick-Up: ESP Designed LH-301B.", 
				(float) 6.66, (float) 649.00, TipoStrumento.PIZZICO, Brand.ESP,
				new Date(2017-1900,06,06), AdminIP, "p",
				10, 0, null, true);
		productList.add(ltd_alexi);
		
		Product CASIO_SA46 = new Product(UUID.randomUUID(), "Casio SA46", 150, 
				"Piu' che un giocattolo: "
				+ "d'altronde i piu' piccoli devono sviluppare sui 32 tasti la "
				+ "passione per la musica fin dall'inizio.", (float) 1.2, (float) 45.00, 
				TipoStrumento.TASTIERA, Brand.CASIO, new Date(2016-1900,06,06), AdminIP, "s",
				15, 8, LivelloStrumento.PRINCIPIANTE, false);
		productList.add(CASIO_SA46);
		
		return productList;
		
	}

}
