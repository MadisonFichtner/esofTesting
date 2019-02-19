package Participation;
import org.junit.* ;
import static org.junit.Assert.* ;

/**
 * This is just a simple template for a JUnit test-class for testing 
 * the class Customer.
 */
public class Customer_test {
	private void setupDB() {
		Persistence.wipedb() ;
	}
	
	@Test
	public void participationValueTest() {
		System.out.println("** Testing getParticipationValue ...") ;
		Customer C = new Customer(0,"Duffy Duck","") ;
		assertEquals(C.participationValue(), 0) ; 
	}
	
	@Test
	public void getCostToPayTest() { 
		setupDB() ;
		ApplicationLogic SUT = new ApplicationLogic() ;
		
		System.out.println("** Testing getCostToPay ...") ;
		
		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
		int flowerServiceID = SUT.addService("Flowers online shop", 100) ;
		// let Duffy but 2x participations on Flower-online:
		SUT.addParticipation(duffyID, flowerServiceID) ;
		SUT.addParticipation(duffyID, flowerServiceID) ;

		// Now let's check if the system correctly calculates the participation
		// cost of Duffy:
		Customer C = SUT.findCustomer(duffyID) ;
		assertEquals(C.getCostToPay(), 200) ;
	}
	
	@Test
	public void getDiscountValueTest() {
		setupDB() ;
		ApplicationLogic SUT = new ApplicationLogic() ;
		
		System.out.println("** Testing getDiscountValue and getParticipationGroups ...") ;
		int duffyID = SUT.addCustomer("Duffy Duck", "");
		
		int flowerServiceID = SUT.addService("Flowers online shop", 50) ;	//Initially make the discount not applicable (50 is less than 100)
		SUT.addParticipation(duffyID, flowerServiceID) ;
		
		int carServiceID = SUT.addService("Cars online shop", 200) ;	//Add applicable services
		int houseServiceID = SUT.addService("House online shop", 200) ;
		int trainServiceID = SUT.addService("Train online shop", 200) ;
		int planeServiceID = SUT.addService("Planes online shop", 200) ;
		SUT.addParticipation(duffyID, carServiceID) ;
		SUT.addParticipation(duffyID, houseServiceID) ;
		SUT.addParticipation(duffyID, trainServiceID) ;
		SUT.addParticipation(duffyID, planeServiceID) ;
		
		Discount_5pack fivePack = new Discount_5pack();
		
		Customer C = SUT.findCustomer(duffyID);	//no discounts applied
		SUT.awardDiscount(duffyID, "D5pack");	//d5 discount
		assertEquals(C.getDiscountValue(), 0);	//discount not applicable so it will = 0
		SUT.remDiscount(duffyID, "D5pack");	//remove discount that was added
		
		SUT.removeService(flowerServiceID);	//remove inappropriate flower service (below 100 participation value)
		flowerServiceID = SUT.addService("Flowers online shop", 200) ;	//Re-add flower service with appropriate participation value
		SUT.addParticipation(duffyID, flowerServiceID) ;
		
		SUT.awardDiscount(duffyID, "D5pack");	//d5 discount
		C = SUT.findCustomer(duffyID);	
		assertEquals(C.getDiscountValue(), 10);	//Since participation value threshold is met for D5 discounts, the discount should equal 10
	}
	
	// and so on ...
}
