package behaviors;

import java.util.Vector;

import src.Athlete;
import src.Client;
import src.Communicator;

public class NotifyNone implements NotificationBehavior {

	@Override
	public void notifyObservers(Vector<Client> observers, Client specific, Vector<Athlete> athletes, String bibNo, Communicator comm, String message) {
		return;
	}

}
