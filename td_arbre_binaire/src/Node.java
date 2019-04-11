import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import jade.core.AID;
import jade.core.Agent;

public class Node extends Agent {
	
	Optional<AID> left, right;
	Optional<Integer> value;
	public Map<Integer,AID> ids;

	public void setup(){
		ids = new HashMap<Integer, AID>();
		value = Optional.empty();
		left = Optional.empty();
		right = Optional.empty();
		addBehaviour(new FactoryBehaviour(this));
	}

	public void setValue(Integer value2) {	
		value = Optional.of(value2);
	}
}
