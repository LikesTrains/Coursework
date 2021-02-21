package src;

public interface Subject {
	public void registerObserver(Observer o);
	public void removeObserver(Client o);
	public void notifyObservers(Communicator cm, String message);
}
