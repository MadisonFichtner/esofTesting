package Participation;

import static org.junit.Assert.*;

import org.junit.Test;

public class Discount_1000_test {
	private void setupDB() {
		Persistence.wipedb() ;
	}
	
	@Test
	public void applicableTest() {
		setupDB();
		ApplicationLogic SUT = new ApplicationLogic() ;
		
		System.out.println("** Testing applicable (1000)  ...") ;
		int duffyID = SUT.addCustomer("Duffy Duck", "");
		
		int flowerServiceID = SUT.addService("Flowers online shop", 1000) ;	//Initially, be less than requirement of 100000 participation value
		SUT.addParticipation(duffyID, flowerServiceID) ;
		
		Discount_1000 onePack = new Discount_1000();
		Customer C = SUT.findCustomer(duffyID);
		boolean applicable = onePack.applicable(C);
		assertEquals(applicable, false);
		//100000
		int carServiceID = SUT.addService("Cars online shop", 100000) ;		//Second, meet the requirement for 100000 participation value
		SUT.addParticipation(duffyID, carServiceID) ;

		onePack = new Discount_1000();
		C = SUT.findCustomer(duffyID);
		applicable = onePack.applicable(C);
		assertEquals(applicable, true);
	}
	
	@Test
	public void calcDiscountTest() {
		setupDB();
		ApplicationLogic SUT = new ApplicationLogic() ;
		
		System.out.println("** Testing calcDiscount (1000)  ...") ;
		int duffyID = SUT.addCustomer("Duffy Duck", "");
		int flowerServiceID = SUT.addService("Flowers online shop", 100) ;
		SUT.addParticipation(duffyID, flowerServiceID) ;
		
		SUT.awardDiscount(duffyID, "D1000eur");	//d5 discount
		Customer C = SUT.findCustomer(duffyID);
		Discount_1000 onePack = new Discount_1000();
		int expected = (50 * 100 * (100 / (1000 *100)));
		assertEquals(onePack.calcDiscount(C), expected);
	}

}
