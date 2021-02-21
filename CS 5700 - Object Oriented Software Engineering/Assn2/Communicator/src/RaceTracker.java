package src;

import java.util.Vector;

public class RaceTracker {
	private Communicator communicator;
	private Vector<Athlete> athletes;
	private Vector<Client> clients;
	private RaceEvent raceEvent;
	
	
	public RaceTracker() throws Exception {
		this("12000");
	}
	
	public RaceTracker(String port) throws Exception {
		setClients(new Vector<Client>());
		setAthletes(new Vector<Athlete>());		
		communicator = new Communicator(Integer.parseInt(port));
		setRaceEvent(new RaceEvent());
		
        CompetentMessageProcessor processor = new CompetentMessageProcessor("Receiver");
        this.getCommunicator().setProcessor(processor);
        this.getCommunicator().setTrackedRace(this);
        this.start();
	}
	
	public void start() {
		this.communicator.start();
	}
	
	public void stop() {
		this.communicator.stop();
		this.communicator.close();
	}
	
	public Communicator getCommunicator() {
		return communicator;
	}
	public void setCommunicator(Communicator communicator) {
		this.communicator = communicator;
	}
	public Vector<Athlete> getAthletes() {
		return athletes;
	}
	public void setAthletes(Vector<Athlete> athletes) {
		this.athletes = athletes;
	}

	public Vector<Client> getClients() {
		return clients;
	}

	public void setClients(Vector<Client> clients) {
		this.clients = clients;
	}

	public RaceEvent getRaceEvent() {
		return raceEvent;
	}

	public void setRaceEvent(RaceEvent raceEvent) {
		this.raceEvent = raceEvent;
	}	
}
