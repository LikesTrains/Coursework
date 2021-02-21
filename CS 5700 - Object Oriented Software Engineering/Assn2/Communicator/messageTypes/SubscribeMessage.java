package messageTypes;

import java.net.InetAddress;
import java.net.UnknownHostException;

import behaviors.*;
import src.Athlete;
import src.Client;
import src.RaceTracker;

public class SubscribeMessage extends Message {

	public SubscribeMessage() {
		this.raceEventsBehavior = new DoesNotAffectRace();
		this.notificationBehavior = new NotifyOne();//In the original implementation, if a client subbed to an athlete after the athlete reached some end stage, the client wouldn't be notified about it. Fixed that issue. This also makes feedback from subscribing more instant
		this.athleteBehavior = new AddAthleteObserver();
		this.clientsBehavior = new DoesNotAffectClients();
	}

	@Override
	public void execute(RaceTracker rt, String[] message) {
		this.raceEventsBehavior.actOnRace(rt.getRaceEvent(), null, null, null);
		this.clientsBehavior.affectClients(rt.getClients(), null);
			
		
		Client toAdd = new Client();
		try {
			toAdd.setAddress(InetAddress.getByName(message[2].substring(1)));
			toAdd.setEndPoint(message[3]);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		Athlete addable = null;
		for (Athlete ath : rt.getAthletes()) {
			if (ath.getBibID().equals(message[1])) {
				//System.out.println(ath.getBibID()+" is equal to "+message[1]);
				addable = ath;
			}
		}
		
		this.athleteBehavior.affectAthletes(rt.getAthletes(), addable, toAdd);
		if(!addable.getStatus().equals("Registered")) {
			String updateM = "Status,"+addable.getBibID()+","+addable.getStatus()+","+addable.getStartTime()+","+addable.getDistanceCovered()+","+addable.getLastUpdatedTime()+","+addable.getFinishTime();
			this.notificationBehavior.notifyObservers(null, toAdd, null, null, rt.getCommunicator(), updateM);
		}
	}

}
