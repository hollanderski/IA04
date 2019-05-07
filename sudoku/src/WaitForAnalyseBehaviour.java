import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitForAnalyseBehaviour extends CyclicBehaviour {

	public WaitForAnalyseBehaviour(Agent a) {
		this.myAgent = a;
	}

	@Override
	public void action() {
		ACLMessage msg = super.myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		if (msg != null) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				Cell[] cells = mapper.readValue(msg.getContent(), Cell[].class);
				ArrayList<Integer> existingValues = new ArrayList<Integer>();
				for (Cell cell : cells) {
					int value = cell.valueIfExists();
					if (value != 0) existingValues.add(value);
				}
				for (Cell cell : cells)
					cell.remove(existingValues);
				for (Cell cell : cells) {
					for(int value : cell.getPossibleValues()) {
						boolean unique=true;
						for(Cell item : cells) {
							if(item==cell) continue;
							if(item.getPossibleValues().contains(value)) {
								unique=false;
								break;
							}
						}
						
						if(unique) {
							ArrayList<Integer> possibleValues = new ArrayList<Integer>();
							possibleValues.add(value);
							cell.setPossibleValues(possibleValues);
						}
					}
				}
				ArrayList<Integer> twoPV = new ArrayList<Integer>();
				for (int i = 0; i < 9 ; i++) {
					if (cells[i].getPossibleValues().size() == 2)
						twoPV.add(i);
				}
				if (twoPV.size() >= 2) {
					for (int ind1 : twoPV) {
						if (cells[ind1].getPossibleValues().size() != 2)
							continue;
						int a = cells[ind1].getPossibleValues().get(0);
						int b = cells[ind1].getPossibleValues().get(1);
						int ind2ok = -1;
						for (int ind2 : twoPV) {
							if (ind1 == ind2)
								continue;
							if (cells[ind2].getPossibleValues().size() != 2)
								continue;
							int c = cells[ind2].getPossibleValues().get(0);
							int d = cells[ind2].getPossibleValues().get(1);
							if ((a == c || a == d) && (b == c || b == d)) {
								if (ind2ok != -1) {
									ind2ok = -1;
									break;
								}
								else
									ind2ok = ind2;
							}
						}
						if (ind2ok >= 0) {
							for (Cell c : cells) {
								if (c != cells[ind1] && c != cells[ind2ok]) {
									ArrayList<Integer> toRemove = new ArrayList<Integer>();
									toRemove.add(a);
									toRemove.add(b);
									c.remove(toRemove);
								}
							}
						}
					}
				}
				
				
				ACLMessage reply = msg.createReply();
				reply.setContent(mapper.writeValueAsString(cells));
				reply.setPerformative(ACLMessage.INFORM);
				super.myAgent.send(reply);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			
		}
	}
}
