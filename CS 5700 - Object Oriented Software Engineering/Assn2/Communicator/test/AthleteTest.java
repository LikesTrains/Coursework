package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Vector;

import org.junit.jupiter.api.Test;

import src.Athlete;
import src.Client;
import src.Observer;

class AthleteTest {

	@Test
	void testConstruction() {
		Athlete athlete = new Athlete();	
		assertEquals(athlete.getObservers(), new Vector<Observer>());
		assertNull(athlete.getAge());
		assertNull(athlete.getBibID());
		assertNull(athlete.getDistanceCovered());
		assertNull(athlete.getFinishTime());
		assertNull(athlete.getFirstName());
		assertNull(athlete.getGender());
		assertNull(athlete.getLastName());
		assertNull(athlete.getLastUpdatedTime());
		assertNull(athlete.getStartTime());
		assertNull(athlete.getStatus());
		
		athlete = new Athlete("123","John", "Santa", "Male", "4", "In-Progress", "5", "Jan 1", "Jan 2", "Jan 3");
		
		assertEquals("123",athlete.getBibID());
		assertEquals("John",athlete.getFirstName());
		assertEquals("Santa",athlete.getLastName());
		assertEquals("Male",athlete.getGender());
		assertEquals("4",athlete.getAge());
		assertEquals("In-Progress",athlete.getStatus());
		assertEquals("5",athlete.getDistanceCovered());
		assertEquals("Jan 1",athlete.getStartTime());
		assertEquals("Jan 2",athlete.getLastUpdatedTime());
		assertEquals("Jan 3",athlete.getFinishTime());
		assertEquals(athlete.getObservers(), new Vector<Observer>());
	}
	
	@Test
	void testObservers() {
		Athlete athlete = new Athlete("123","John", "Santa", "Male", "4", "In-Progress", "5", "Jan 1", "Jan 2", "Jan 3");
		
		Client ct1, ct2;
		
		ct1 = new Client();
		ct2 = new Client();
		ct2.setEndPoint("15");
		
		assertEquals(athlete.getObservers(), new Vector<Observer>());
		athlete.registerObserver(ct1);
		
		assertNotEquals(athlete.getObservers(), new Vector<Observer>());
		assertSame(athlete.getObservers().get(0), ct1);
		
		athlete.registerObserver(ct2);
		assertSame(athlete.getObservers().get(1), ct2);
		assertEquals(((Client) athlete.getObservers().get(1)).getEndPoint(), "15");
		
		athlete.removeObserver(ct2);
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {athlete.getObservers().get(1);});
		assertSame(athlete.getObservers().get(0), ct1);
		
		athlete.removeObserver(ct1);
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {athlete.getObservers().get(0);});
	}

}
