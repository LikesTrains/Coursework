package behaviors;

import java.util.Vector;

import src.Client;

public class AffectsClients implements ClientsBehavior {

	@Override
	public void affectClients(Vector<Client> observers, Client newObs) {
		Client checker = null;
		
		for (Client test:observers) {
			if (test.getAddress()==newObs.getAddress()&& test.getEndPoint()==newObs.getEndPoint())
				checker = test;
		}
		
		if (checker==null) {
			observers.add((Client) newObs);
		}
	}
}
