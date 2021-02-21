package src;

public class RaceEvent {

	public RaceEvent(String title, String distance, String status) {
		super();
		this.title = title;
		this.distance = distance;
		this.status = status;
	}

	private String title;
	private String distance;
	private String status;
	
	public RaceEvent() {
		this.status = "hasNotStarted";
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
