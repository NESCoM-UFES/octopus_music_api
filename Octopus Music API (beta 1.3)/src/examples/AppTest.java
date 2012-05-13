package examples;
import octopus.*;

public class AppTest {
	public static void main(String[] args) {
		
		Note nota1;

			nota1 = Notes.getA();
			
			System.out.println(nota1.getName());
			System.out.println(nota1.getMidiValue());
			System.out.println(nota1.getFrequency());

		
		
	}
}
