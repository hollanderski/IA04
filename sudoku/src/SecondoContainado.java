import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;

public class SecondoContainado {
	public static ContainerController cc = null;
	public static void main(String[] args){
		String SECONDO_PROPERTIES="properties/secondoContainer.txt";
		Runtime rt = Runtime.instance();
		Profile p = null;
		
		try{
			
			p = new ProfileImpl(SECONDO_PROPERTIES);
			cc = rt.createAgentContainer(p);
			AgentController simulateur = cc.createNewAgent("Simulation", "Simulation", null),
					sudoku = cc.createNewAgent("Sudoku", "Sudoku", null);
			
			simulateur.start();
			sudoku.start();	
			
			for (int i = 0; i < 27 ; i++) {
				AgentController analyse = cc.createNewAgent("Analyse"+Integer.toString(i), "Analyse", null);
				analyse.start();
			}
					
		} catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
}
