package messageTypes;

import behaviors.*;
import src.RaceTracker;

public class RaceStartedMessage extends Message {
	public RaceStartedMessage(){
		this.raceEventsBehavior = new AffectsRace();
		this.notificationBehavior = new NotifyAll();
		this.athleteBehavior = new DoesNotAffectAthletes();
		this.clientsBehavior = new DoesNotAffectClients();
	}
	
	
	@Override
	public void execute(RaceTracker rt, String[] message) {
		
		this.raceEventsBehavior.actOnRace(rt.getRaceEvent(), message[1], message[2], "Race Started");
			
		this.athleteBehavior.affectAthletes(rt.getAthletes(), null, null);
		this.clientsBehavior.affectClients(rt.getClients(), null);
		
		String messageToSend = "Race,"+rt.getRaceEvent().getTitle()+","+rt.getRaceEvent().getDistance();
		this.notificationBehavior.notifyObservers(rt.getClients(),null,rt.getAthletes(),"",rt.getCommunicator(), messageToSend);
	}

}
