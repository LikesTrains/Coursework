package behaviors;

import java.util.Vector;

import src.Athlete;
import src.Observer;

public class CreatesAthlete implements AthleteBehavior {

	@Override
	public void affectAthletes(Vector <Athlete> athletes, Athlete newAth, Observer notUsed) {
		if (newAth!=null) {
			athletes.add(newAth);
		}
	}
}
