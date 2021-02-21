package behaviors;

import java.util.Vector;

import src.Client;

public interface ClientsBehavior {
	public void affectClients(Vector <Client> clients, Client newObs);
}