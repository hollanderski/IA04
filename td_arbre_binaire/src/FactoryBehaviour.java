import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class FactoryBehaviour extends CyclicBehaviour {
	
	public FactoryBehaviour(Agent a) {
		super.myAgent = a;
	}
	
	@Override
	public void action() {
		ACLMessage message = super.myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		if(message!=null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				Request r = mapper.readValue(message.getContent(), Request.class);
				String action = r.getAction();
				switch(action) {
					case "insert" :
						super.myAgent.addBehaviour(new InsertBehaviour(super.myAgent, message));
						break;
					case "exist" :
						super.myAgent.addBehaviour(new ExistBehaviour(super.myAgent, message));
						break;
					case "display" :
						super.myAgent.addBehaviour(new DisplayBehaviour(super.myAgent, message));
						break;
				}
				((Node) super.myAgent).ids.put(Integer.parseInt(message.getConversationId()), message.getSender());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		message = super.myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		if(message!=null) {
			System.out.println("convid="+message.getConversationId());
			int id = Integer.parseInt(message.getConversationId());
			ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
			inform.setContent(message.getContent());
			inform.addReceiver(((Node)super.myAgent).ids.get(id));
			inform.setConversationId(message.getConversationId());
			((Node)super.myAgent).ids.remove(id);
			super.myAgent.send(inform);
		}
	}

}
