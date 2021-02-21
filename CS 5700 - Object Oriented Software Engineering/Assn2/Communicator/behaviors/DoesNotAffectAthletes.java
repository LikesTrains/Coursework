package behaviors;

import java.util.Vector;

import src.Athlete;
import src.Observer;

public class DoesNotAffectAthletes implements AthleteBehavior {

	@Override
	public void affectAthletes(Vector<Athlete> athletes, Athlete usedAth, Observer usedObs) {
		return;
	}

}
