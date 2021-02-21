package test;

import static java.nio.charset.StandardCharsets.UTF_16BE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;
import src.Client;
import src.Communicator;
import src.DummyMessageProcessor;

class ClientTest {

	@Test
	void testClientConstruction() throws UnknownHostException {
		Client testClient = new Client();
		assertEquals("", testClient.getEndPoint());
		assertEquals(false, testClient.getHasBeenNotified());
		assertEquals(InetAddress.getLocalHost().toString(), testClient.getAddress().toString());
		
		testClient.setEndPoint("123");
		assertEquals("123", testClient.getEndPoint());
		
		testClient.setHasBeenNotified(true);
		assertEquals(true, testClient.getHasBeenNotified());
		
		testClient.setAddress(InetAddress.getByName("101.2.3.4"));
		assertEquals("/101.2.3.4", testClient.getAddress().toString());
	}
	
	@Test
	void testUpdate() throws Exception {
		Client testClient = new Client();
		testClient.setEndPoint("123");
		
		Communicator used = new Communicator(12000);
		DummyMessageProcessor processor = new DummyMessageProcessor("test");
		used.setProcessor(processor);
		
		Communicator catcher = new Communicator(123);
		DummyMessageProcessor processor2 = new DummyMessageProcessor("test2");
		catcher.setProcessor(processor2);
		
		testClient.update(used, "Testing");
		
        DatagramPacket packet = catcher.getMessage(100);
        assertNotNull(packet);

        String message = new String( packet.getData(), 0, packet.getLength(), UTF_16BE);
        assertEquals("Testing", message);
        
        assertEquals(used.getLocalPort(), packet.getPort());
        
        used.close();
        catcher.close();
	}

}
