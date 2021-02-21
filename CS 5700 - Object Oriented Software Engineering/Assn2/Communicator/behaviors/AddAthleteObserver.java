package behaviors;

import java.util.Vector;

import src.Athlete;
import src.Observer;

public class AddAthleteObserver implements AthleteBehavior {

	@Override
	public void affectAthletes(Vector<Athlete> athletes, Athlete usedAth, Observer toAdd) {
		Athlete inArr = null;
		for(Athlete ath : athletes) {
			if(ath.getBibID().equals(usedAth.getBibID())) {
				inArr = ath;
			}
		}
		if (inArr != null) {
			inArr.registerObserver(toAdd);
		}
	}

}
