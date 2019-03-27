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
			AgentController dispatcher = cc.createNewAgent("DispatchAgent", "DispatchAgent", null);
			dispatcher.start(); 
		} catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
}
