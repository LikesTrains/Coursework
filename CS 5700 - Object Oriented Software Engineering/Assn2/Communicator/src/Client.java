package src;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Client extends Observer {

	private String endPoint;
	private boolean hasBeenNotified;
	private InetAddress address;
	
	
	@Override
	public void update(Communicator cm, String message) {
		
		try {
			cm.send(message, address, Integer.parseInt(endPoint));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Client() {
		this.endPoint = "";
		this.hasBeenNotified = false;
		this.setAddress(null);
		try {
			this.setAddress(InetAddress.getLocalHost());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
	}
	public Client(String endPoint) {
		this.endPoint = endPoint;
		this.hasBeenNotified = false;
		try {
			this.setAddress(InetAddress.getLocalHost());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
	}
	
	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public boolean getHasBeenNotified() {
		return hasBeenNotified;
	}

	public void setHasBeenNotified(boolean hasBeenNotified) {
		this.hasBeenNotified = hasBeenNotified;
	}

	public InetAddress getAddress() {
		return address;
	}

	public void setAddress(InetAddress address) {
		this.address = address;
	}
}
