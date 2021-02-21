package behaviors;

import src.RaceEvent;

public class DoesNotAffectRace implements RaceEventsBehavior {

	@Override
	public void actOnRace(RaceEvent r, String name, String distance, String status) {
		return;
	}

}
