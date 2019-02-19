package Participation;
import org.junit.* ;
import static org.junit.Assert.* ;

import java.util.Map;

/**
 * This is just a simple template for Junit test-class for testing
 * the class ApplicationLogic. Testing this class is a bit more
 * complicated. It uses database, which form an implicit part of
 * the state of ApplicationLogic. After each test case, you need to
 * reset the content of the database to the value it had, before
 * the test case. Otherwise, multiple test cases will interfere
 * with each other, which makes debugging hard should you find error.
 * 
 */
public class ApplicationLogic_test {

	/**
	 * Provide a functionality to reset the database. Here I will just
	 * delete the whole database file. 
	 */
	private void setupDB() {
		Persistence.wipedb() ;
	}
	
	
	@Test
	public void resolveTest() {
		setupDB() ;
		ApplicationLogic SUT = new ApplicationLogic() ;
		
		System.out.println("** Testing getCostToPay ...") ;
		
		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
		int duffyID1 = SUT.addCustomer("Daffy Duck", "") ;
		int flowerServiceID = SUT.addService("Flowers online shop", 100000) ;
		int flowerServiceID1 = SUT.addService("Flowers online shop", 100) ;
		// let Duffy but 2x participations on Flower-online:
		SUT.addParticipation(duffyID, flowerServiceID) ;
		SUT.addParticipation(duffyID1, flowerServiceID1) ;
		SUT.awardDiscount(duffyID, "D1000eur");
		SUT.awardDiscount(duffyID1, "D1000eur");
		// Now let's check if the system correctly calculates the participation
		// cost of Duffy:
		Map<Customer,Integer> map = SUT.resolve();
	}
	
	
	@Test
	public void removeServiceTest() {
		setupDB() ;
		ApplicationLogic SUT = new ApplicationLogic() ;
		
		System.out.println("** Testing removeService ...") ;
		
		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
		int flowerServiceID = SUT.addService("Flowers online shop", 100) ;
		// let Duffy but 2x participations on Flower-online:
		SUT.addParticipation(duffyID, flowerServiceID) ;
		
		assertEquals(SUT.serviceExists(flowerServiceID), true)  ;	//Test if service was corectly added and is available
		SUT.removeService(flowerServiceID) ;	//Remove service (only one exists)
 
		assertEquals(SUT.serviceExists(flowerServiceID), false) ;	//Test if service was correctly removed and is no longer available
		SUT.removeService(flowerServiceID);							//Try removing service again despite it already being removed
		assertEquals(SUT.serviceExists(flowerServiceID), false);	//Should return false since there are no services at all.
	}
}
