package behaviors;

import java.util.Vector;

import src.Athlete;
import src.Observer;

public class UpdateAthleteStatus implements AthleteBehavior {

	@Override
	public void affectAthletes(Vector<Athlete> athletes, Athlete usedAth, Observer notUsed) {
		Athlete original = null;
		for (Athlete ath : athletes) {
			if(ath.getBibID().equals(usedAth.getBibID())) {
				original = ath;
			}
		}
		if(original != null) {
			if(usedAth.getDistanceCovered()!=null) {
				original.setDistanceCovered(usedAth.getDistanceCovered());
			}
			if(usedAth.getStatus()!=null) {
				original.setStatus(usedAth.getStatus());
			}
			if(usedAth.getFinishTime()!=null) {
				original.setFinishTime(usedAth.getFinishTime());
			}
			if(usedAth.getLastUpdatedTime()!=null) {
				original.setLastUpdatedTime(usedAth.getLastUpdatedTime());
			}
			if(usedAth.getStartTime()!=null) {
				original.setStartTime(usedAth.getStartTime());
			}
		}
	}

}
