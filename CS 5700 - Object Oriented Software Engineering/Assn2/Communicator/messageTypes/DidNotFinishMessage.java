package messageTypes;

import behaviors.DoesNotAffectClients;
import behaviors.DoesNotAffectRace;
import behaviors.NotifySubs;
import behaviors.UpdateAthleteStatus;
import src.Athlete;
import src.RaceTracker;

public class DidNotFinishMessage extends Message {

	public DidNotFinishMessage() {
		this.raceEventsBehavior = new DoesNotAffectRace();
		this.notificationBehavior = new NotifySubs();
		this.athleteBehavior = new UpdateAthleteStatus();
		this.clientsBehavior = new DoesNotAffectClients();
	}

	@Override
	public void execute(RaceTracker rt, String[] message) {
		this.raceEventsBehavior.actOnRace(rt.getRaceEvent(), "", "", "");
		
		Athlete toUpdate = new Athlete(message[1],null, null,null,null,"DidNotFinish",null,null,message[2],"0");
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
