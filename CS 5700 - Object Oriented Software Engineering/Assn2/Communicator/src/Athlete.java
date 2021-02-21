package src;

import java.util.List;
import java.util.Vector;

public class Athlete implements Subject {
	public Athlete(String bibID, String firstName, String lastName, String gender, String age, String status,
			String distanceCovered, String startTime, String lastUpdatedTime, String finishTime) {
		super();
		this.bibID = bibID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.age = age;
		this.status = status;
		this.distanceCovered = distanceCovered;
		this.startTime = startTime;
		this.lastUpdatedTime = lastUpdatedTime;
		this.finishTime = finishTime;
		this.observers = new Vector<Client>();
	}


	private String bibID;
	private String firstName, lastName;
	private String gender;
	private String age;
	private String status;
	private String distanceCovered;
	private String startTime, lastUpdatedTime, finishTime;
	private Vector<Client> observers;
	
	public Athlete() {
		this.observers = new Vector<Client>();
	}


	public String getBibID() {
		return bibID;
	}


	public String getFirstName() {
		return firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public String getGender() {
		return gender;
	}


	public String getAge() {
		return age;
	}


	public List<Client> getObservers() {
		return observers;
	}


	@Override
	public void registerObserver(Observer o) {
		this.observers.add((Client)o);
		
	}


	@Override
	public void removeObserver(Client o) {
		Client toRem = null;
		for (Client ct:this.observers) {
			if(o.getAddress().equals(ct.getAddress())&&o.getEndPoint().equals(ct.getEndPoint())) {
				//System.out.println(o.getAddress()+":"+o.getEndPoint()+" is equal to "+ct.getAddress()+":"+ct.getEndPoint());
				toRem=ct;
			}
			else {
				//System.out.println(o.getAddress()+":"+o.getEndPoint()+" is not equal to "+ct.getAddress()+":"+ct.getEndPoint());
			}
		}
		int index = this.observers.indexOf(toRem);
		if(toRem!=null) {
			this.observers.remove(index);
		}
		
	}


	@Override
	public void notifyObservers(Communicator cm, String message) {
		for (Client obs : this.observers) {
			obs.setHasBeenNotified(true);
			obs.update(cm, message);
		}
	}


	public String getStartTime() {
		return startTime;
	}


	public String getStatus() {
		return status;
	}


	public String getDistanceCovered() {
		return distanceCovered;
	}


	public String getLastUpdatedTime() {
		return lastUpdatedTime;
	}


	public String getFinishTime() {
		return finishTime;
	}


	public void setBibID(String bibID) {
		this.bibID = bibID;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public void setAge(String age) {
		this.age = age;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public void setDistanceCovered(String distanceCovered) {
		this.distanceCovered = distanceCovered;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public void setLastUpdatedTime(String lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}


	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}


	public void setObservers(Vector<Client> observers) {
		this.observers = observers;
	}


}
