package src;
import java.net.InetAddress;

public interface IMessageProcessor {
    void process(RaceTracker rt, String message, InetAddress address, int port);
}
