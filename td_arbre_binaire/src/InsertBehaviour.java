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
public class InsertBehaviour extends OneShotBehaviour {

	private ACLMessage msg = null;
	
	public InsertBehaviour(Agent parent, ACLMessage m){
		super.myAgent=parent;
		msg = m;
	}
	
	public void sendInform(boolean b, Request query) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		((Node) super.myAgent).setValue(query.getValue());
		query.setFlag(b);
		String content = mapper.writeValueAsString(query);
		ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
		inform.setContent(content);
		inform.addReceiver(msg.getSender());
		inform.setConversationId(msg.getConversationId());
		super.myAgent.send(inform);
	}
	
	@Override
	public void action() {
		if(msg!=null){
			ObjectMapper mapper = new ObjectMapper();
			try {
				Request query=mapper.readValue(msg.getContent(), Request.class);
				if(!((Node) super.myAgent).value.isPresent()){
					((Node) super.myAgent).setValue(query.getValue());
					sendInform(true, query);
				}
				else {
					Integer currentValue = ((Node) super.myAgent).value.get();
					if(currentValue == query.getValue()){
						sendInform(false, query);
					}
					else {
						boolean left = currentValue > query.getValue();
						Optional<AID> fils = left ? ((Node) super.myAgent).left : ((Node) super.myAgent).right;
						if (fils.isPresent()) {
							// send msg to kid
							msg.clearAllReceiver();
							msg.addReceiver(fils.get());
							msg.setSender(super.myAgent.getAID());
							super.myAgent.send(msg);
						} else {
							// create new agent
							AgentController ac;
							try {
								ac = SecondoContainado.cc.createNewAgent("Node"+Integer.toString(query.getValue()), "Node", null);
								ac.start();
								AID node = new AID("Node"+Integer.toString(query.getValue()), AID.ISLOCALNAME);
								if (left)
									((Node)(super.myAgent)).left = Optional.of(node);
								else
									((Node)(super.myAgent)).right = Optional.of(node);
								msg.clearAllReceiver();
								msg.addReceiver(node);
								msg.setSender(super.myAgent.getAID());
								super.myAgent.send(msg);
							} catch (StaleProxyException e) {
								e.printStackTrace();
							}
						}						
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
	}
}
