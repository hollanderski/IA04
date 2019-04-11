import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class SubscribeBehaviour extends OneShotBehaviour {

	public SubscribeBehaviour(Agent agent) {
		this.myAgent = agent;
	}
	
	@Override
	public void action() {
		AID simulateur = new AID("Simulation", AID.ISLOCALNAME);
		ACLMessage msg = new ACLMessage(ACLMessage.SUBSCRIBE);
		msg.addReceiver(simulateur);
		this.myAgent.send(msg);
	}
}
