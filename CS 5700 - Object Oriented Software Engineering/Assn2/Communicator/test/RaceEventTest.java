package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import src.RaceEvent;

class RaceEventTest {

	@Test
	void testConstruction() {
		 RaceEvent testRace = new RaceEvent();
		 
		 assertNull(testRace.getDistance());
		 assertNull(testRace.getTitle());
		 
		 testRace = new RaceEvent("Test Race","123", "notSent");
		 assertEquals(testRace.getTitle(), "Test Race");
		 assertEquals(testRace.getDistance(), "123");
		 assertEquals(testRace.getStatus(), "notSent");
	}

}
