import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class SendNotificationBehaviour extends TickerBehaviour {

	public SendNotificationBehaviour(Agent a, long period) {
		super(a, period);
	}

	@Override
	public void onTick(){
		
		ACLMessage message = new ACLMessage (ACLMessage.REQUEST);
		ObjectMapper data = new ObjectMapper();
		
		try {
			message.setContent(data.writeValueAsString(((Simulation) super.myAgent).getAnalyst()));
			message.addReceiver(new AID("Sudoku", AID.ISLOCALNAME));
			super.myAgent.send(message);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} 
	}

}
