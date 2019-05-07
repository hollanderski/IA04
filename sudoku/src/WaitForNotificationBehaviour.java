import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitForNotificationBehaviour extends CyclicBehaviour {

	@Override
	public void action() {
		ACLMessage message = super.myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		if (message != null) {
			((Sudoku)super.myAgent).displaySudoku();
			ObjectMapper mapper = new ObjectMapper();
			try {
				ArrayList<String> analysts = mapper.readValue(message.getContent(), mapper.getTypeFactory().constructCollectionType(ArrayList.class, String.class));
				int i = 0;
				for (String analyst : analysts) {
					ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
					msg.addReceiver(new AID(analyst, AID.ISLOCALNAME));
					Cell[] cells = i < 9 ? ((Sudoku)super.myAgent).getLine(i) : i < 18 ? ((Sudoku)super.myAgent).getColumn(i-9) : ((Sudoku)super.myAgent).getSquare(i-18);
					msg.setContent(mapper.writeValueAsString(cells));
					
					super.myAgent.addBehaviour(new WaitForMyAnalyseBehaviour(super.myAgent, msg, i));
					i++;
					
				}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
