package src;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Vector;

import messageTypes.*;

public class CompetentMessageProcessor implements IMessageProcessor {

    private String name;

    public CompetentMessageProcessor(String name) {
        this.setName(name);
    }
    @Override
    public void process(RaceTracker rt, String message, InetAddress address, int port) {
        if (message==null) {
            System.out.println("Null string");
            return;
        }

        if (address==null) {
            System.out.println("Null address");
            return;
        }
        
        String[] terms = message.split(",");
        Message toProcess = null;
        /*
        for (String str:terms) {
        	System.out.println(str);
        }        
        */
        if("Race".equals(terms[0])) {
        	toProcess = new RaceStartedMessage();
        }
        else if("Registered".equals(terms[0])) {
        	//System.out.println("Registering");
        	toProcess = new RegisteredUpdateMessage();
        }
        else if("DidNotStart".equals(terms[0])) {
        	toProcess = new DidNotStartUpdateMessage();
        }
        else if("Started".equals(terms[0])) {
        	toProcess = new StartedUpdateMessage();
        }
        else if("OnCourse".equals(terms[0])) {
        	toProcess = new OnCourseUpdateMessage();
        }
        else if("DidNotFinish".equals(terms[0])) {
        	toProcess = new DidNotFinishMessage();
        }
        else if("Finished".equals(terms[0])) {
        	toProcess = new FinishedUpdateMessage();
        }
        else if ("Hello".equals(terms[0])) {
        	toProcess = new HelloMessage();
        	Vector<String> moddedTerms = new Vector<String>(Arrays.asList(terms));
        	moddedTerms.add(address.toString());
        	moddedTerms.add(String.valueOf(port));
        	terms = moddedTerms.toArray(new String[0]);
        }
        else if ("Subscribe".equals(terms[0])) {
        	toProcess = new SubscribeMessage();
        	Vector<String> moddedTerms = new Vector<String>(Arrays.asList(terms));
        	moddedTerms.add(address.toString());
        	moddedTerms.add(String.valueOf(port));
        	terms = moddedTerms.toArray(new String[0]);
        }
        else if ("Unsubscribe".equals(terms[0])) {
        	toProcess = new UnsubscribeMessage();
        	Vector<String> moddedTerms = new Vector<String>(Arrays.asList(terms));
        	moddedTerms.add(address.toString());
        	moddedTerms.add(String.valueOf(port));
        	terms = moddedTerms.toArray(new String[0]);
        }
        
        if(toProcess!=null) {
        	//System.out.println(String.format("%s received: %s from %s:%d", name, message, address.toString(), port));
        	toProcess.execute(rt, terms);            
        }
        
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
