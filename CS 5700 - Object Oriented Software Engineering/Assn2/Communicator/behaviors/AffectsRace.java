package behaviors;

import src.RaceEvent;

public class AffectsRace implements RaceEventsBehavior {

	@Override
	public void actOnRace(RaceEvent r, String name, String distance, String status) {
		if (name != null)
			r.setTitle(name);
		if (distance != null)
			r.setDistance(distance);
		if (status!=null)
			r.setStatus(status);
	}
}
