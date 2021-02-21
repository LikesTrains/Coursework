package behaviors;

import java.util.Vector;

import src.Client;

public class DoesNotAffectClients implements ClientsBehavior {

	@Override
	public void affectClients(Vector<Client> clients, Client newObs) {
		return;
	}
}
