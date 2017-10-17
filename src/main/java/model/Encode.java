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
     * @throws NoSuchAlgorithmException if an error occurs with the encryption of the string, 
     * i.e. the algorithm used for the encryption can't be found
	 */
	public static String cryptingString(String messageToEncrypt) throws NoSuchAlgorithmException{
		
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(StandardCharsets.UTF_8.encode(messageToEncrypt));
		
		return (String.format("%032x", new BigInteger(1, md5.digest())));
		
	}

}