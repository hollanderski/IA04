import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class DispatchBehaviour extends CyclicBehaviour {

	public DispatchBehaviour(Agent a){
		super.myAgent=a;
	}
	
	@Override
	public void action() {
		ACLMessage message = super.myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		if(message!=null){
			String par = message.getContent();
			ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
			request.setContent(par);
			request.addReceiver(((DispatchAgent) super.myAgent).getRoot());
			//DFService.search()
		}
		message = super.myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		if(message!=null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				Request r = mapper.readValue(message.getContent(), Request.class);
				System.out.println(r.getResult());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
