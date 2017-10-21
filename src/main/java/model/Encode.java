package model;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The Encode class provides the encryption of elements
 * @author Paolo D'Arienzo
 * @version 1.5
 */
public class Encode {
	
	/**
	 * Encrypts the value passed to the method
	 * @param messageToEncrypt String that will be encrypted
	 * @return String encrypted
	 */
	public static String cryptingString(String messageToEncrypt){
		
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO
			//Alghoritm not found
			e.printStackTrace();
		}
		md5.update(StandardCharsets.UTF_8.encode(messageToEncrypt));
		
		return (String.format("%032x", new BigInteger(1, md5.digest())));
		
	}

}