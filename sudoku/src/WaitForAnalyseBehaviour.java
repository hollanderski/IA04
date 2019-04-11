import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitForAnalyseBehaviour extends CyclicBehaviour {

	public WaitForAnalyseBehaviour(Agent a) {
		this.myAgent = a;
	}

	@Override
	public void action() {
		ACLMessage msg = super.myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		if (msg != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				Cell[] cells = mapper.readValue(msg.getContent(), Cell[].class);
				ArrayList<Integer> existingValues = new ArrayList<Integer>();
				for (Cell cell : cells) {
					int value = cell.valueIfExists();
					if (value != 0) existingValues.add(value);
				}
				for (Cell cell : cells)
					cell.remove(existingValues);
				ACLMessage reply = msg.createReply();
				reply.setContent(mapper.writeValueAsString(cells));
				reply.setPerformative(ACLMessage.INFORM);
				super.myAgent.send(reply);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			
		}
	}
}
