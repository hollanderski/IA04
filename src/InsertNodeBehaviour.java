import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

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
public class InsertNodeBehaviour extends OneShotBehaviour {

	
	public InsertNodeBehaviour(Node parent){
			super.myAgent=parent;
	}
	
	@Override
	public void action() {
		ACLMessage message = super.myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		if(message!=null){
			String par = message.getContent();
			ObjectMapper mapper = new ObjectMapper();
			try {
				Request query=mapper.readValue(par, Request.class);
				Node current = ((Node) super.myAgent);
				
				
				if(!current.value.isPresent()){
					current.setValue(query.getValue());
				}
				else{
					Integer currentValue = current.value.get();
					if(currentValue == query.getValue()){
						// return impossible
					}
					else {
						Optional<AID> fils = currentValue > query.getValue() ? current.left : current.right;
						if (fils.isPresent()) {
							// send msg to kid
							String result = mapper.writeValueAsString(query);
							ACLMessage inform = new ACLMessage(ACLMessage.REQUEST);
							inform.addReceiver(fils.get());
							inform.setContent(result);
							super.myAgent.send(inform);
							
						} else {
							// create new agent
							AgentController ac;
							try {
								
								ac = SecondoContainado.cc.createNewAgent("Node", "Node", null);
								Node agent =((Node) ac);
								agent.setValue(query.getValue());
								
							} catch (StaleProxyException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
						}
						
					}
				}
				query.setResult("OK");
				
				String result = mapper.writeValueAsString(query);
				ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
				inform.addReceiver(message.getSender());
				inform.setContent(result);
				super.myAgent.send(inform);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}

		}
	}
}
