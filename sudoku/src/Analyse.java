import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;

public class Analyse extends Agent{

	// Doit s'enregistrer par un subscribe � la classe Simulation
	// Puis attendre
	
	public void setup(){
		SequentialBehaviour seqBe = new SequentialBehaviour(this);
		seqBe.addSubBehaviour(new SubscribeBehaviour(this));
		seqBe.addSubBehaviour(new WaitForAnalyseBehaviour(this));
		addBehaviour(seqBe);
	}
}
