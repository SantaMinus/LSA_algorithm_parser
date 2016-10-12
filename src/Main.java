
import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		LSAParser parser = new LSAParser();
		GUI gui = new GUI(parser);
		gui.paint();	
		return;
	}
}