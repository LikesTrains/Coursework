package behaviors;

import java.util.Vector;

import src.Athlete;
import src.Communicator;
import src.Client;

public class NotifySubs implements NotificationBehavior {

	@Override
	public void notifyObservers(Vector<Client> clients,Client specific, Vector<Athlete> athletes, String bibNo, Communicator comm, String message) {
		Athlete guy = new Athlete();
		
		for (Athlete ath : athletes) {
			if(ath.getBibID().equals(bibNo)) {
				guy = ath;
			}
		}
		guy.notifyObservers(comm, message);
	}

}
