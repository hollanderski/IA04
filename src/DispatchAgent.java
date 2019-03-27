import jade.core.AID;
import jade.core.Agent;
import jade.wrapper.StaleProxyException;

public class DispatchAgent extends Agent {
	
	private AID root;
	
	public void setup(){
		try {
			this.getContainerController().createNewAgent("root", "Node", null);
			root = new AID("root", AID.ISLOCALNAME);
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
		addBehaviour(new DispatchBehaviour(this));
	}

	public AID getRoot() {
		return root;
	}
}
