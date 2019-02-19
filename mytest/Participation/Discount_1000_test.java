package Participation;

import static org.junit.Assert.*;

import org.junit.Test;

public class Discount_1000_test {
	private void setupDB() {
		Persistence.wipedb() ;
	}
	
	@Test
	public void applicableTest() {
		setupDB() ;
		ApplicationLogic SUT = new ApplicationLogic() ;
		
		System.out.println("** Testing participationValue ...") ;
		
		int duffyID = SUT.addCustomer("Duffy Duck", "") ;
		int flowerServiceID = SUT.addService("Flowers online shop", 100) ;
		// let Duffy but 2x participations on Flower-online:
		SUT.addParticipation(duffyID, flowerServiceID) ;
		
		fail("Not yet implemented");
	}

}
