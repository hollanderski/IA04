import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitForSubscriptionsBehaviour extends Behaviour {

	private int count;

	public WaitForSubscriptionsBehaviour(Agent a) {
		this.myAgent = a;
		count = 0;
	}

	@Override
	public void action() {
		ACLMessage msg = this.myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.SUBSCRIBE));
		if (msg != null) {
			count++;
			AID aid = msg.getSender();
			((Simulation)this.myAgent).addAnalyst(aid.getLocalName());
		}
	}

	@Override
	public boolean done() {
		return count >= 27;
	}

}
