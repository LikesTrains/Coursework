package messageTypes;

import java.net.InetAddress;
import java.net.UnknownHostException;

import behaviors.*;
import src.Athlete;
import src.Client;
import src.RaceTracker;

public class UnsubscribeMessage extends Message {

	public UnsubscribeMessage() {
		this.raceEventsBehavior = new DoesNotAffectRace();
		this.notificationBehavior = new NotifyNone();
		this.athleteBehavior = new RemoveAthleteObserver();
		this.clientsBehavior = new DoesNotAffectClients();
	}

	@Override
	public void execute(RaceTracker rt, String[] message) {
		this.raceEventsBehavior.actOnRace(rt.getRaceEvent(), null, null, null);
		this.clientsBehavior.affectClients(rt.getClients(), null);
		this.notificationBehavior.notifyObservers(null, null, null, null, null, null);		
		
		Client toRem = new Client();
		try {
			toRem.setAddress(InetAddress.getByName(message[2].substring(1)));
			toRem.setEndPoint(message[3]);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		Athlete removable = null;
		for (Athlete ath : rt.getAthletes()) {
			if (ath.getBibID().equals(message[1]))
				removable = ath;
		}
		this.athleteBehavior.affectAthletes(rt.getAthletes(), removable, toRem);
	}

}
