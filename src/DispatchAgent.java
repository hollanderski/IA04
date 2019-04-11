import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.StaleProxyException;

public class DispatchAgent extends Agent {
	
	private AID root;
	
	public void setup(){
		try {
			this.getContainerController().createNewAgent("root", "Node", null).start();
			root = new AID("root", AID.ISLOCALNAME);
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
		addBehaviour(new DispatchBehaviour(this));
		
		Request r = new Request();
		r.setAction("insert");
		r.setValue(10);
		sendMessage(r);
		
		r.setValue(20);
		sendMessage(r);
		
		r.setAction("exist");
		sendMessage(r);
		
		r.setValue(10);
		sendMessage(r);
		
		r.setValue(5);
		sendMessage(r);
		
		r.setAction("display");
		sendMessage(r);
		
		r.setAction("insert");
		r.setValue(5);
		sendMessage(r);
		
		r.setValue(7);
		sendMessage(r);
		
		r.setValue(30);
		sendMessage(r);
		
		r.setValue(18);
		sendMessage(r);
		
		r.setValue(2);
		sendMessage(r);
		
		r.setValue(11);
		sendMessage(r);
		
		r.setValue(42);
		sendMessage(r);
		
		r.setValue(95);
		sendMessage(r);
		
		r.setAction("display");
		sendMessage(r);
		
	}

	private void sendMessage(Request r) {
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		ObjectMapper map = new ObjectMapper();
		try {
			String str = map.writeValueAsString(r);
			msg.setContent(str);
			msg.addReceiver(this.getAID());
			this.send(msg);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	public AID getRoot() {
		return root;
	}
}
