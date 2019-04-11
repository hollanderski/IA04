import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class DispatchBehaviour extends CyclicBehaviour {
	static public int id = 0;
	
	public DispatchBehaviour(Agent a){
		super.myAgent=a;
	}
	
	@Override
	public void action() {
		ACLMessage message = super.myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		if(message!=null){
			ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
			request.setContent(message.getContent());
			request.addReceiver(((DispatchAgent) super.myAgent).getRoot());
			request.setConversationId(Integer.toString(id++));
			super.myAgent.send(request);
		}
		message = super.myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		if(message==null)
			message = super.myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE));
		if(message!=null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				Request r = mapper.readValue(message.getContent(), Request.class);
				System.out.println("Result:" + r.getAction() + " " + r.getValue() + " " + r.getResult() + " " + r.getFlag());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
