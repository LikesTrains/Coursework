package behaviors;

import java.util.Vector;

import src.Athlete;
import src.Client;
import src.Observer;

public class RemoveAthleteObserver implements AthleteBehavior {

	@Override
	public void affectAthletes(Vector<Athlete> athletes, Athlete usedAth, Observer toRemove) {
		Athlete inArr = null;
		for(Athlete ath : athletes) {
			if(ath.getBibID().equals(usedAth.getBibID())) {
				inArr = ath;
			}
		}
		if (inArr != null) {
			//System.out.println("Oh yeah mate, I'm here");
			inArr.removeObserver((Client)toRemove);
		}
	}

}
