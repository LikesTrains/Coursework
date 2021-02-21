package test;

import src.*;

import static java.nio.charset.StandardCharsets.UTF_16BE;
import static org.junit.jupiter.api.Assertions.*;

import java.net.DatagramPacket;
import java.net.InetAddress;

import org.junit.jupiter.api.Test;

class SystemTest {

	// In this scenario, clients say hello before the race starts, and subscribe to their given athletes as well.
	// They should only be notified of the events as they happen
	@Test
	void testScenario1() throws Exception {
		RaceTracker rt = new RaceTracker();
		IMessageProcessor competent = new CompetentMessageProcessor("DirtyCommie");
				
		rt.getCommunicator().setProcessor(competent);
		rt.getCommunicator().start();
		rt.getCommunicator().setTrackedRace(rt);
		
		
		Communicator client1Comm = new Communicator(12001);
		Communicator client2Comm = new Communicator(12002);
		Communicator simComm = new Communicator(12003);
		
		//Testing Registered message
		simComm.send("Registered,1,1:00,Tiny,Rick,pickle,420", InetAddress.getLocalHost(), rt.getCommunicator().getLocalPort());
		simComm.send("Registered,2,1:00,Jonah,Hill,Male,30", InetAddress.getLocalHost(), rt.getCommunicator().getLocalPort());
		simComm.send("Registered,3,1:00,Quitting,McQuitter,Male,22", InetAddress.getLocalHost(), rt.getCommunicator().getLocalPort());
		Thread.sleep(10);
		assertNotNull(rt.getAthletes().get(0));
		assertNotNull(rt.getAthletes().get(1));
		assertNotNull(rt.getAthletes().get(2));
		
		
		//Testing this case of the Hello message
		client1Comm.send("Hello", InetAddress.getLocalHost(), rt.getCommunicator().getLocalPort());
		client2Comm.send("Hello", InetAddress.getLocalHost(), rt.getCommunicator().getLocalPort());
		Thread.sleep(10);
		assertNotNull(rt.getClients().get(0));
		assertNotNull(rt.getClients().get(1));
		
		DatagramPacket packet = client1Comm.getMessage(10);
		assertNotNull(packet);
		String message = new String( packet.getData(), 0, packet.getLength(), UTF_16BE);
		assertEquals("Athlete,1,Tiny,Rick,pickle,420",message);
		packet = client1Comm.getMessage(10);
		assertNotNull(packet);
		message = new String( packet.getData(), 0, packet.getLength(), UTF_16BE);
		assertEquals("Athlete,2,Jonah,Hill,Male,30",message);
		packet = client1Comm.getMessage(10);
		assertNotNull(packet);
		message = new String( packet.getData(), 0, packet.getLength(), UTF_16BE);
		assertEquals("Athlete,3,Quitting,McQuitter,Male,22",message);
		
		packet = client2Comm.getMessage(10);
		assertNotNull(packet);
		message = new String( packet.getData(), 0, packet.getLength(), UTF_16BE);
		assertEquals("Athlete,1,Tiny,Rick,pickle,420",message);
		packet = client2Comm.getMessage(10);
		assertNotNull(packet);
		message = new String( packet.getData(), 0, packet.getLength(), UTF_16BE);
		assertEquals("Athlete,2,Jonah,Hill,Male,30",message);
		packet = client2Comm.getMessage(10);
		assertNotNull(packet);
		message = new String( packet.getData(), 0, packet.getLength(), UTF_16BE);
		assertEquals("Athlete,3,Quitting,McQuitter,Male,22",message);
		
		//Testing the subscribe message
		client1Comm.send("Subscribe,1", InetAddress.getLocalHost(), rt.getCommunicator().getLocalPort());
		Thread.sleep(10);
		assertNotNull(rt.getAthletes().get(0).getObservers().get(0));
		client2Comm.send("Subscribe,1", InetAddress.getLocalHost(), rt.getCommunicator().getLocalPort());
		Thread.sleep(10);
		assertNotNull(rt.getAthletes().get(0).getObservers().get(1));
		client1Comm.send("Subscribe,2", InetAddress.getLocalHost(), rt.getCommunicator().getLocalPort());
		Thread.sleep(10);
		assertNotNull(rt.getAthletes().get(1).getObservers().get(0));
		client2Comm.send("Subscribe,2", InetAddress.getLocalHost(), rt.getCommunicator().getLocalPort());
		Thread.sleep(10);
		assertNotNull(rt.getAthletes().get(1).getObservers().get(1));
		client2Comm.send("Subscribe,3", InetAddress.getLocalHost(), rt.getCommunicator().getLocalPort());
		Thread.sleep(10);
		assertNotNull(rt.getAthletes().get(2).getObservers().get(0));
		
		//Testing the unsubscribe message
		client2Comm.send("Unsubscribe,2", InetAddress.getLocalHost(), rt.getCommunicator().getLocalPort());
		Thread.sleep(20);
		assertThrows(ArrayIndexOutOfBoundsException.class, ()->{  rt.getAthletes().get(1).getObservers().get(1);});
		
		client1Comm.send("Unsubscribe,1", InetAddress.getLocalHost(), rt.getCommunicator().getLocalPort());
		Thread.sleep(20);
		assertThrows(ArrayIndexOutOfBoundsException.class, ()->{  rt.getAthletes().get(0).getObservers().get(1);});
		
		//Testing the race started message		
		simComm.send("Race,DisneyXD,20", InetAddress.getLocalHost(), rt.getCommunicator().getLocalPort());
		Thread.sleep(10);
		
		packet = client1Comm.getMessage(10);
		assertNotNull(packet);
		message = new String( packet.getData(), 0, packet.getLength(), UTF_16BE);
		assertEquals("Race,DisneyXD,20",message);
		
		packet = client2Comm.getMessage(10);
		assertNotNull(packet);
		message = new String( packet.getData(), 0, packet.getLength(), UTF_16BE);
		assertEquals("Race,DisneyXD,20",message);
		
		assertEquals("DisneyXD",rt.getRaceEvent().getTitle());
		assertEquals("20",rt.getRaceEvent().getDistance());
		
		packet = client2Comm.getMessage(10);
		assertNull(packet);
		packet = client1Comm.getMessage(10);
		assertNull(packet);
		
		//Testing athlete started messages
		simComm.send("Started,1,1:01", InetAddress.getLocalHost(), rt.getCommunicator().getLocalPort());
		simComm.send("Started,2,1:02", InetAddress.getLocalHost(), rt.getCommunicator().getLocalPort());
		
		
		packet = client2Comm.getMessage(10);
		assertNotNull(packet);
		message = new String( packet.getData(), 0, packet.getLength(), UTF_16BE);
		assertEquals("Status,1,Started,1:01,0,1:01,0",message);
		packet = client2Comm.getMessage();
		assertNull(packet);
		
		packet = client1Comm.getMessage(10);
		assertNotNull(packet);
		message = new String( packet.getData(), 0, packet.getLength(), UTF_16BE);
		assertEquals("Status,2,Started,1:02,0,1:02,0",message);
		packet = client1Comm.getMessage();
		assertNull(packet);
		
		//Testing athlete did not start message
		simComm.send("DidNotStart,3,1:02", InetAddress.getLocalHost(), rt.getCommunicator().getLocalPort());
		Thread.sleep(10);
		
		packet = client2Comm.getMessage(10);
		assertNotNull(packet);
		message = new String( packet.getData(), 0, packet.getLength(), UTF_16BE);
		assertEquals("Status,3,DidNotStart,0,0,1:02,1:02",message);
		packet = client2Comm.getMessage();
		assertNull(packet);
		
		//Testing on course update messages
		simComm.send("OnCourse,1,1:05,15", InetAddress.getLocalHost(), rt.getCommunicator().getLocalPort());
		simComm.send("OnCourse,2,1:05,10", InetAddress.getLocalHost(), rt.getCommunicator().getLocalPort());
		
		Thread.sleep(10);
		
		packet = client2Comm.getMessage(10);
		assertNotNull(packet);
		message = new String( packet.getData(), 0, packet.getLength(), UTF_16BE);
		assertEquals("Status,1,OnCourse,1:01,15,1:05,0",message);
		packet = client2Comm.getMessage();
		assertNull(packet);
		
		packet = client1Comm.getMessage(10);
		assertNotNull(packet);
		message = new String( packet.getData(), 0, packet.getLength(), UTF_16BE);
		assertEquals("Status,2,OnCourse,1:02,10,1:05,0",message);
		packet = client1Comm.getMessage();
		assertNull(packet);
		
		//Testing did not finish message
		simComm.send("DidNotFinish,2,1:10", InetAddress.getLocalHost(), rt.getCommunicator().getLocalPort());
		
		Thread.sleep(10);
		
		packet = client1Comm.getMessage(10);
		assertNotNull(packet);
		message = new String( packet.getData(), 0, packet.getLength(), UTF_16BE);
		assertEquals("Status,2,DidNotFinish,1:02,10,1:10,0",message);
		packet = client1Comm.getMessage();
		assertNull(packet);
		
		// Testing finished race message
		simComm.send("Finished,1,1:10", InetAddress.getLocalHost(), rt.getCommunicator().getLocalPort());
		
		Thread.sleep(10);
		
		packet = client2Comm.getMessage(10);
		assertNotNull(packet);
		message = new String( packet.getData(), 0, packet.getLength(), UTF_16BE);
		assertEquals("Status,1,Finished,1:01,20,1:10,1:10",message);
		packet = client2Comm.getMessage();
		assertNull(packet);
		packet = client1Comm.getMessage();
		assertNull(packet);
		
		//clean up after yourself you animal
		client1Comm.close();
		client2Comm.close();
		simComm.close();
		rt.getCommunicator().close();
	}

}
