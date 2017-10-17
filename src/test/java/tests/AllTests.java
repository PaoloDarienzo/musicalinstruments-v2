package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Grouping all tests.
 * @author Paolo D'Arienzo
 * @version 1.5
 */

@RunWith(Suite.class)
@SuiteClasses({ TestProduct.class, TestUser.class, TestPurchase.class })
public class AllTests {

}
