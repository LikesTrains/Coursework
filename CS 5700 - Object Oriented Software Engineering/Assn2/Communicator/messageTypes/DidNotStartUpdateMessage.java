package messageTypes;

import behaviors.*;
import src.Athlete;
import src.RaceTracker;

public class DidNotStartUpdateMessage extends Message {

	public DidNotStartUpdateMessage() {
		this.raceEventsBehavior = new DoesNotAffectRace();
		this.notificationBehavior = new NotifySubs();
		this.athleteBehavior = new UpdateAthleteStatus();
		this.clientsBehavior = new DoesNotAffectClients();
	}
	
	@Override
	public void execute(RaceTracker rt, String[] message) {
		//System.out.println("I'm here");
		
		this.raceEventsBehavior.actOnRace(rt.getRaceEvent(), "", "", "");
		
		Athlete toUpdate = new Athlete(message[1],null, null,null,null,"DidNotStart","0","0",message[2],message[2]);
		this.athleteBehavior.affectAthletes(rt.getAthletes(), toUpdate, null);		
		this.clientsBehavior.affectClients(rt.getClients(), null);
		
		Athlete printable = null;
		for (Athlete ath : rt.getAthletes()) {
			if (ath.getBibID().equals(message[1]))
				printable = ath;
		}
		if (printable!=null) {
			String messageToSend = "Status,"+printable.getBibID()+","+printable.getStatus()+","+printable.getStartTime()+","+printable.getDistanceCovered()+","+printable.getLastUpdatedTime()+","+printable.getFinishTime();
			this.notificationBehavior.notifyObservers(rt.getClients(),null,rt.getAthletes(),printable.getBibID(),rt.getCommunicator(), messageToSend);
		}
	}

}
