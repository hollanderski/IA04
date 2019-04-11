import java.util.ArrayList;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SequentialBehaviour;

public class Simulation extends Agent {

	// Attente des subscribe
	// D�clenche les it�rations (TickerBehaviour) : 
	// envoie n messages � chaque tick pour chaque agent enregistr� en donnant le nom de l'Agent (enregistrer les AIDs dans une liste)

	private ArrayList<String> listAIDS = new ArrayList<String>();
	// compteur de messages � incr�menter et remettre � 0 � 27
	// Si le compteur est � 0 --> ligne 1, si le compteur est � 9 --> colonne 0

	public void setup(){
		SequentialBehaviour seqBe = new SequentialBehaviour(this);
		seqBe.addSubBehaviour(new WaitForSubscriptionsBehaviour(this));
		seqBe.addSubBehaviour(new SendNotificationBehaviour(this, 500));
		addBehaviour(seqBe);
	}
	
	public ArrayList<String> getAnalyst(){
		return this.listAIDS;
	}
	
	public void addAnalyst(String analyst) {
		listAIDS.add(analyst);
	}
}
