package messageTypes;

import behaviors.*;
import src.Athlete;
import src.RaceTracker;

public class RegisteredUpdateMessage extends Message {

	public RegisteredUpdateMessage(){
		this.raceEventsBehavior = new DoesNotAffectRace();
		this.notificationBehavior = new NotifyAll();
		this.athleteBehavior = new CreatesAthlete();
		this.clientsBehavior = new DoesNotAffectClients();
	}
	
	@Override
	public void execute(RaceTracker rt, String[] message) {
		this.raceEventsBehavior.actOnRace(null, "", "", "");
		
		Athlete toAdd = new Athlete(message[1],message[3], message[4],message[5],message[6],"Registered",null,null,message[2],"");
		
		this.athleteBehavior.affectAthletes(rt.getAthletes(), toAdd, null);
		this.clientsBehavior.affectClients(rt.getClients(), null);
		
		String messageToSend = "Athlete,"+message[1]+","+message[3]+","+message[4]+","+message[5]+","+message[6];
		this.notificationBehavior.notifyObservers(rt.getClients(),null,null,"",rt.getCommunicator(), messageToSend);
	}

}
