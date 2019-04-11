import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.AID;
import jade.core.Agent;
import jade.core.AgentContainer;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
// OneShot
public class DisplayBehaviour extends OneShotBehaviour {

	private ACLMessage msg = null;
	
	public DisplayBehaviour(Agent parent, ACLMessage m){
		super.myAgent=parent;
		msg = m;
	}
	
	@Override
	public void action() {
		if(msg!=null){
			Node current = ((Node) super.myAgent);
			if (!current.left.isPresent() && !current.right.isPresent()) {
				msg.setPerformative(ACLMessage.PROPAGATE);
				msg.clearAllReceiver();
				msg.addReceiver(msg.getSender());
				msg.setSender(current.getAID());
				Request r = new Request();
				r.setAction("display");
				r.setResult(current.value.isPresent() ? Integer.toString(current.value.get()) : "");
				ObjectMapper mapper = new ObjectMapper();
				try {
					msg.setContent(mapper.writeValueAsString(r));
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				current.send(msg);	
			} else {
				msg.clearAllReceiver();
				msg.setSender(current.getAID());
				int i = 0;
				if (current.left.isPresent()) {
					msg.addReceiver(current.left.get());
					i++;
				}
				if (current.right.isPresent()) {
					msg.addReceiver(current.right.get());
					i++;	
				}
				current.addBehaviour(new DisplayWaitBehaviour(current, i));
				current.send(msg);
			}
		}
	}
}
