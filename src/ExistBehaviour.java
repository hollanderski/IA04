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
public class ExistBehaviour extends OneShotBehaviour {

	private ACLMessage msg = null;
	
	public ExistBehaviour(Agent parent, ACLMessage m){
		super.myAgent=parent;
		msg = m;
	}
	
	public void sendInform(boolean b, Request query) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
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
					sendInform(false, query);
				}
				else {
					Integer currentValue = ((Node) super.myAgent).value.get();
					if(currentValue == query.getValue()){
						sendInform(true, query);
					}
					else {
						Optional<AID> fils = currentValue > query.getValue() ? ((Node) super.myAgent).left : ((Node) super.myAgent).right;
						if (fils.isPresent()) {
							// send msg to kid
							msg.clearAllReceiver();
							msg.addReceiver(fils.get());
							super.myAgent.send(msg);
						} else {
							sendInform(false, query);
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
