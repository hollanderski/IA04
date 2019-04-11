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
public class DisplayWaitBehaviour extends Behaviour {

	public boolean done() {
		return done;
	}
	
	private boolean oneMore;
	private boolean done = false;
	private String left, right;
	
	public DisplayWaitBehaviour(Agent parent, int nbOfChildren){
		super.myAgent=parent;
		this.oneMore = nbOfChildren == 2;
		left = new String("");
		right = new String("");
	}
	
	@Override
	public void action() {
		ACLMessage message = super.myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE));
		if(message!=null) {	
			ObjectMapper mapper = new ObjectMapper();
			Request r;
			try {
				r = mapper.readValue(message.getContent(), Request.class);
				Node current = ((Node) super.myAgent);
				AID sender = message.getSender();
				if (current.left.isPresent() && sender.equals(current.left.get()))
					left = r.getResult();
				else 
					right = r.getResult();
				
				if (!oneMore) {
					r.setResult(" (" + left + " " + (current.value.isPresent() ? Integer.toString(current.value.get()) : "" ) + " " + right + ") ");
					message.setContent(mapper.writeValueAsString(r));
					message.clearAllReceiver();
					message.setSender(current.getAID());
					message.addReceiver(current.ids.get(Integer.parseInt(message.getConversationId())));
					current.ids.remove(Integer.parseInt(message.getConversationId()));	
					current.send(message);
					done = true;
				}
				else oneMore = false;
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
