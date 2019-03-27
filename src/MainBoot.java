import jade.wrapper.AgentContainer;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;

public class MainBoot {

	public static void main(String[] args){
		String MAIN_PROPERTIES="properties/mainContainer.txt";
		Runtime rt = Runtime.instance();
		Profile p = null;
		
		try{
			p = new ProfileImpl(MAIN_PROPERTIES);
			AgentContainer mc = rt.createMainContainer(p);
		} catch(Exception ex){
			System.out.println("Non.");
		}
	}
}
