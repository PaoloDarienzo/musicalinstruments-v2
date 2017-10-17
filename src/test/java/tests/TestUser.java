/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.junit.Test;

import model.Encode;
import model.TipoCliente;
import model.User;

/**
 * This class contains tests the user.
 * @author Paolo D'Arienzo
 * @version 1.5
 */
public class TestUser {

	/**
	 * Tests the creation of <i>User</i> and the <i>get</i> and <i>set</i> methods.
	 * @throws NoSuchAlgorithmException if an error occurs with the encryption of the string
	 */
	@Test
	public void testCreation() throws NoSuchAlgorithmException {
		
		List<User> users = InitializationForTests.userInit();

		/* Testing:
		 * - User creation
		 * - password encryption
		 * - some of the get and set methods
		 * - Field of the user that refers to a Enum type
		 */
		assertEquals(users.get(0).getNome(), "Lucia");
		assertEquals(users.get(0).getCognome(), "Crivellini");
		assertEquals(users.get(0).getMail(), "luciacrive995@gmail.com");
		assertEquals(users.get(0).getNomeUtente(), "Lucia_crive");
		assertEquals(users.get(0).getCittaDiResidenza(), "Verona");
		assertEquals(users.get(0).getPassword(), Encode.cryptingString("Krifpol5878%lf!"));
		assertEquals(users.get(0).getNumeroCellulare(), "93980908890");
		users.get(0).setNumeroCellulare("1234567890");
		assertEquals(users.get(0).getNumeroCellulare(), "1234567890");
		
		assertEquals(users.get(3).getNome(), "Mario");
		assertEquals(users.get(3).getMail(), "mario.rossi@gmail.com");
		assertEquals(users.get(3).getTipo(), TipoCliente.OCCASIONALE);
		
	}

}
