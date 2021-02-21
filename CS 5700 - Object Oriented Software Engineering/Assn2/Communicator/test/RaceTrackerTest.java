package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Vector;

import org.junit.jupiter.api.Test;

import src.Athlete;
import src.Client;
import src.RaceTracker;

class RaceTrackerTest {

	@Test
	void testConstructor() throws Exception {
		RaceTracker test = new RaceTracker();
		
		assertEquals(new Vector<Athlete>(), test.getAthletes());
		assertEquals(new Vector<Client>(), test.getClients());
		assertEquals(12000, test.getCommunicator().getLocalPort());
		
		
		//at the end of everything, close the port.
		test.getCommunicator().close();
	}

}
