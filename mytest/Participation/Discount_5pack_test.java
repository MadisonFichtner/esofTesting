package Participation;

import static org.junit.Assert.*;

import org.junit.Test;

public class Discount_5pack_test {
	private void setupDB() {
		Persistence.wipedb() ;
	}
	
	@Test
	public void applicableTest() {
		setupDB();
		ApplicationLogic SUT = new ApplicationLogic() ;
		
		System.out.println("** Testing applicable (5)  ...") ;
		int duffyID = SUT.addCustomer("Duffy Duck", "");
		
		int flowerServiceID = SUT.addService("Flowers online shop", 200) ;	//Initially only have 1, less than 5 so it's not valid
		SUT.addParticipation(duffyID, flowerServiceID) ;
		
		Discount_5pack fivePack = new Discount_5pack();
		Customer C = SUT.findCustomer(duffyID);
		boolean applicable = fivePack.applicable(C);
		assertEquals(applicable, false);

		int carServiceID = SUT.addService("Cars online shop", 50) ;		//Second, have 5 as required, but have on not meet the 100 participation value threshold
		int houseServiceID = SUT.addService("House online shop", 200) ;
		int trainServiceID = SUT.addService("Train online shop", 200) ;
		int planeServiceID = SUT.addService("Planes online shop", 200) ;
		SUT.addParticipation(duffyID, carServiceID) ;
		SUT.addParticipation(duffyID, houseServiceID) ;
		SUT.addParticipation(duffyID, trainServiceID) ;
		SUT.addParticipation(duffyID, planeServiceID) ;

		fivePack = new Discount_5pack();
		C = SUT.findCustomer(duffyID);
		applicable = fivePack.applicable(C);
		assertEquals(applicable, false);
		
		int goodiesServiceID = SUT.addService("goodies online shop", 200);
		SUT.addParticipation(duffyID, goodiesServiceID) ;
		fivePack = new Discount_5pack();
		C = SUT.findCustomer(duffyID);
		applicable = fivePack.applicable(C);
		assertEquals(applicable, true);
	}
	
	@Test
	public void calcDiscountTest() {
		setupDB();
		ApplicationLogic SUT = new ApplicationLogic() ;
		
		System.out.println("** Testing calcDiscount (5)  ...") ;
		int duffyID = SUT.addCustomer("Duffy Duck", "");
		int flowerServiceID = SUT.addService("Flowers online shop", 100) ;
		SUT.addParticipation(duffyID, flowerServiceID) ;
		
		SUT.awardDiscount(duffyID, "D5pack");	//d5 discount
		Customer C = SUT.findCustomer(duffyID);
		Discount_5pack fivePack = new Discount_5pack();
		int expected = 10;
		assertEquals(fivePack.calcDiscount(C), expected);
	}

}
