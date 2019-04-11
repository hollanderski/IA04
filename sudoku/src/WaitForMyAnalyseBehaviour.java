import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

public class WaitForMyAnalyseBehaviour extends AchieveREInitiator {

	private int i;
	
	public WaitForMyAnalyseBehaviour(Agent a, ACLMessage msg, int i) {
		super(a, msg);
		this.i = i;
	}
	
	@Override
	public void handleInform(ACLMessage inform) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Cell cells[] = mapper.readValue(inform.getContent(), Cell[].class);
			if (i < 9)
				((Sudoku)super.myAgent).updateLine(i, cells);
			else if (i < 18)
				((Sudoku)super.myAgent).updateColumn(i-9, cells);
			else
				((Sudoku)super.myAgent).updateSquare(i-18, cells);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
