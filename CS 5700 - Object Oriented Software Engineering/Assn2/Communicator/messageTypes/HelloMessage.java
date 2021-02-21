package messageTypes;

import java.net.InetAddress;
import java.net.UnknownHostException;

import behaviors.*;
import src.Athlete;
import src.Client;
import src.RaceTracker;

public class HelloMessage extends Message {

	public HelloMessage() {
		this.raceEventsBehavior = new DoesNotAffectRace();
		this.notificationBehavior = new NotifyOne();
		this.athleteBehavior = new DoesNotAffectAthletes();
		this.clientsBehavior = new AffectsClients();
	}

	@Override
	public void execute(RaceTracker rt, String[] message) {
		this.raceEventsBehavior.actOnRace(rt.getRaceEvent(), null, null, "");
		this.athleteBehavior.affectAthletes(rt.getAthletes(), null, null);
		
		Client toAdd = new Client();
		try {
			toAdd.setAddress(InetAddress.getByName(message[1].substring(1)));
			toAdd.setEndPoint(message[2]);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		Client checker = null;
		
		for (Client ct:rt.getClients()) {
			if (ct.getAddress()==toAdd.getAddress() && ct.getEndPoint()==toAdd.getEndPoint()) {
				checker = ct;
			}
		}
		
		if(checker == null) {
			this.clientsBehavior.affectClients(rt.getClients(), toAdd);
			
			if(rt.getRaceEvent().getStatus()!="hasNotStarted") {
				toAdd.setHasBeenNotified(true);
				String messageToSend = "Race,"+rt.getRaceEvent().getTitle()+","+rt.getRaceEvent().getDistance();
				this.notificationBehavior.notifyObservers(rt.getClients(),toAdd,rt.getAthletes(),"",rt.getCommunicator(), messageToSend);
			}
			if(!rt.getAthletes().isEmpty()) {
				toAdd.setHasBeenNotified(true);
				for (Athlete ath : rt.getAthletes()) {
					String messageToSend = "Athlete,"+ath.getBibID()+","+ath.getFirstName()+","+ath.getLastName()+","+ath.getGender()+","+ath.getAge();
					this.notificationBehavior.notifyObservers(rt.getClients(),toAdd,rt.getAthletes(),"",rt.getCommunicator(), messageToSend);
				}
			}
		}
	}

}
