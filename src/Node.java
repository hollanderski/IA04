import java.util.Optional;

import jade.core.AID;
import jade.core.Agent;

public class Node extends Agent {
	
	Optional<AID> left, right;
	Optional<Integer> value;
	
	public void setup(){
		//addBehaviour(new InsertNodeBehaviour(this));
	}
// et il y aura RetrieveBehaviour
	public void setValue(Integer value2) {
		
		value = Optional.of(value2);
		
	}
}
