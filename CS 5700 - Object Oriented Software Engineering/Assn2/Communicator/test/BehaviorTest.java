package test;
import behaviors.*;
import src.*;

import static java.nio.charset.StandardCharsets.UTF_16BE;
import static org.junit.jupiter.api.Assertions.*;

import java.net.DatagramPacket;
import java.util.Vector;

import org.junit.jupiter.api.Test;

class BehaviorTest {
	
	// Testing Athlete Behaviors
	@Test
	void AthleteBehaviorsTest() {
		//starting elements
		Vector<Athlete> athletes = new Vector<Athlete>();
		Athlete ath1 = new Athlete("123","John", "Santa", "Male", "4", "In-Progress", "5", "Jan 1", "Jan 2", "Jan 3");
		Athlete ath2 = new Athlete("456","Dan", "Man", "Male", "42", "Did-Not-Finish", "3", "Jan 1", "Jan 2", "Jan 3");
		
		//AthAdder
		AthleteBehavior adder = new CreatesAthlete();
		
		adder.affectAthletes(athletes, ath1, null);
		adder.affectAthletes(athletes, ath2, null);
		
		adder.affectAthletes(athletes, null, null);
		
		assertThrows(ArrayIndexOutOfBoundsException.class, () ->  {athletes.get(2);});
		
		assertSame(ath1, athletes.elementAt(0));
		assertSame(ath2, athletes.elementAt(1));		
		
		//updater
		AthleteBehavior athUpdater = new UpdateAthleteStatus();
		Athlete toChange = new Athlete("123", null, null, null, null, "Started", "12", null, "Feb 1", "Feb 3");
		
		athUpdater.affectAthletes(athletes, toChange, null);
		
		assertEquals("Started", ath1.getStatus());
		assertEquals("12", ath1.getDistanceCovered());
		assertEquals("Feb 1", ath1.getLastUpdatedTime());
		assertEquals("Feb 3", ath1.getFinishTime());
		
		//ObsAdder
		Client ct1 = new Client("10");		
		Client ct2 = new Client("12");
		Client ct3 = new Client("40");
		
		
		AthleteBehavior obsAdder = new AddAthleteObserver();
		
		obsAdder.affectAthletes(athletes, ath1, ct1);		
		assertSame(ct1, athletes.get(0).getObservers().get(0));
		
		obsAdder.affectAthletes(athletes, ath2, ct1);		
		assertSame(ct1, athletes.get(1).getObservers().get(0));
		
		obsAdder.affectAthletes(athletes, ath2, ct2);		
		assertSame(ct2, athletes.get(1).getObservers().get(1));
		
		obsAdder.affectAthletes(athletes, ath2, ct3);		
		assertSame(ct3, athletes.get(1).getObservers().get(2));
		
		//ObsRemover
		AthleteBehavior removesObs = new RemoveAthleteObserver();
		
		Athlete dummy = new Athlete();
		dummy.setBibID("456");
		
		removesObs.affectAthletes(athletes, dummy, ct2);
		
		assertNotSame(ct2, athletes.get(1).getObservers().get(1));
		assertSame(ct3, athletes.get(1).getObservers().get(1));
		
		removesObs.affectAthletes(athletes, dummy, ct3);
		
		assertThrows(ArrayIndexOutOfBoundsException.class, ()->{athletes.get(1).getObservers().get(1);});
		assertSame(ct1, athletes.get(1).getObservers().get(0));
		
		//DoesNothing
		
		AthleteBehavior doNothing = new DoesNotAffectAthletes();
		
		doNothing.affectAthletes(null, null, null);
		
	}
		
	// Testing Race Behaviors
	@Test
	void RaceBehaviorsTest() {
		RaceEvent original = new RaceEvent("Test Event","100", "Not Started");
		
		RaceEventsBehavior affects = new AffectsRace();
		
		affects.actOnRace(original, "Changed event", "150", "Started");
		
		assertEquals("Changed event", original.getTitle());
		assertEquals("150", original.getDistance());
		assertEquals("Started", original.getStatus());
		
		affects.actOnRace(original, null, null, null);
		
		assertEquals("Changed event", original.getTitle());
		assertEquals("150", original.getDistance());
		assertEquals("Started", original.getStatus());
		
		RaceEventsBehavior doesNothing = new DoesNotAffectRace();
		doesNothing.actOnRace(original, "Eyy", "0", "Never starting");
		
		assertEquals("Changed event", original.getTitle());
		assertEquals("150", original.getDistance());
		assertEquals("Started", original.getStatus());
	}
	
	// Testing Client Behaviors
	@Test
	void ClientBehaviorsTest() {
		Vector<Client> clients = new Vector<Client>();
		
		Client ct1 = new Client("150");
		Client ct2 = new Client("151");
		
		ClientsBehavior affects = new AffectsClients();
		
		affects.affectClients(clients, ct1);
		
		assertSame(ct1, clients.get(0));
		
		affects.affectClients(clients, ct2);
		
		assertSame(ct2, clients.get(1));
		
		// should not do anything
		
		affects.affectClients(clients, ct2);
		
		assertSame(ct2, clients.get(1));
		assertThrows(ArrayIndexOutOfBoundsException.class, ()->{clients.get(2);} );
		
		ClientsBehavior doesNothing = new DoesNotAffectClients();
		
		Client ct3 = new Client("152");
		
		doesNothing.affectClients(clients, ct3);
		
		assertSame(ct1, clients.get(0));
		assertSame(ct2, clients.get(1));
		
		assertThrows(ArrayIndexOutOfBoundsException.class,()->{clients.get(2);});
		
	}
	
	@Test
	void notificationBehaviorsTest() throws Exception {
		// set up test clients
		Vector<Client> clients = new Vector<Client>();
		Client ct1 = new Client();
		Client ct2 = new Client();
		Client ct3 = new Client();
		
		ct1.setEndPoint("12001");
		ct2.setEndPoint("12002");
		ct3.setEndPoint("12003");
		
		clients.add(ct1);
		clients.add(ct2);
		clients.add(ct3);
		
		
		// set up test athletes
		Vector<Athlete> athletes = new Vector<Athlete>();
		Athlete ath1 = new Athlete();
		Athlete ath2 = new Athlete();
		Athlete ath3 = new Athlete();
		
		ath1.setBibID("000");
		ath2.setBibID("001");
		ath3.setBibID("002");
		
		athletes.add(ath1);
		athletes.add(ath2);
		athletes.add(ath3);
		
		
		//set up connections between athletes and their observers
		athletes.get(0).registerObserver(ct1);
		athletes.get(0).registerObserver(ct2);
		
		athletes.get(1).registerObserver(ct2);
		athletes.get(1).registerObserver(ct3);
		
		athletes.get(2).registerObserver(ct1);
		athletes.get(2).registerObserver(ct3);
		
		//setting up message
		String mess = "You've been notified!";
		
		//setting up communicators at play
		Communicator sender = new Communicator(12000);
		Communicator listener1 = new Communicator(Integer.parseInt(ct1.getEndPoint()));
		Communicator listener2 = new Communicator(Integer.parseInt(ct2.getEndPoint()));
		Communicator listener3 = new Communicator(Integer.parseInt(ct3.getEndPoint()));
		
		//testing NotifyOne
		
		NotificationBehavior notifier = new NotifyOne();
		
		notifier.notifyObservers(clients, ct1, athletes, "", sender, mess);
		
		DatagramPacket pack = listener1.getMessage(100);		
		assertNotNull(pack);
		
		String check = new String( pack.getData(), 0, pack.getLength(), UTF_16BE);
		assertEquals(check, "You've been notified!");
		assertEquals(sender.getLocalPort(), pack.getPort());
		
		pack = listener2.getMessage(50);
		assertNull(pack);
		pack = listener3.getMessage(50);
		assertNull(pack);
		
		//testing NotifySubs
		notifier = new NotifySubs();
		
		notifier.notifyObservers(clients, ct1, athletes, "000", sender, mess);
		
		pack = listener1.getMessage(100);		
		assertNotNull(pack);
		check = new String( pack.getData(), 0, pack.getLength(), UTF_16BE);
		assertEquals(check, "You've been notified!");
		assertEquals(sender.getLocalPort(), pack.getPort());
		
		pack = listener2.getMessage(100);		
		assertNotNull(pack);
		check = new String( pack.getData(), 0, pack.getLength(), UTF_16BE);
		assertEquals(check, "You've been notified!");
		assertEquals(sender.getLocalPort(), pack.getPort());
		
		pack = listener3.getMessage(50);
		assertNull(pack);
		
		//testing NotifyAll
		notifier = new NotifyAll();
		
		notifier.notifyObservers(clients, ct1, athletes, "000", sender, mess);
		
		pack = listener1.getMessage(100);		
		assertNotNull(pack);
		check = new String( pack.getData(), 0, pack.getLength(), UTF_16BE);
		assertEquals(check, "You've been notified!");
		assertEquals(sender.getLocalPort(), pack.getPort());
		
		pack = listener2.getMessage(100);		
		assertNotNull(pack);
		check = new String( pack.getData(), 0, pack.getLength(), UTF_16BE);
		assertEquals(check, "You've been notified!");
		assertEquals(sender.getLocalPort(), pack.getPort());
		
		pack = listener3.getMessage(100);		
		assertNotNull(pack);
		check = new String( pack.getData(), 0, pack.getLength(), UTF_16BE);
		assertEquals(check, "You've been notified!");
		assertEquals(sender.getLocalPort(), pack.getPort());
		
		//testing NotifyNone
		notifier = new NotifyNone();
		
		notifier.notifyObservers(clients, ct1, athletes, "000", sender, mess);
		
		pack = listener1.getMessage(50);
		assertNull(pack);
		pack = listener2.getMessage(50);
		assertNull(pack);
		pack = listener3.getMessage(50);
		
		//clean up after yourself you animal
		sender.close();
		listener1.close();
		listener2.close();
		listener3.close();
	}
}
