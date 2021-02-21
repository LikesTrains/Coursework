package messageTypes;

import behaviors.*;
import src.*;

public abstract class Message {
	protected RaceEventsBehavior raceEventsBehavior;
	protected NotificationBehavior notificationBehavior;
	protected AthleteBehavior athleteBehavior;
	protected ClientsBehavior clientsBehavior;
	
	public Message() {}
	
	public abstract void execute(RaceTracker rt, String[] message);
}
