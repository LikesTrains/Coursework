package behaviors;

import java.util.Vector;

import src.Athlete;
import src.Client;
import src.Communicator;

public interface NotificationBehavior {
	public void notifyObservers(Vector<Client> clients, Client specific, Vector<Athlete> athletes, String bibNo, Communicator cm, String message);
}
