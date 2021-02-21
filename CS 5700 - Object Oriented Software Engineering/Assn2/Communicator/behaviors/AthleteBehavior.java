package behaviors;

import java.util.Vector;

import src.Athlete;
import src.Observer;

public interface AthleteBehavior {
	public void affectAthletes(Vector <Athlete> athletes, Athlete usedAth, Observer usedObs);
}