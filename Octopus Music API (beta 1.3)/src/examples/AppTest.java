package examples;
import octopus.*;

public class AppTest {
	public static void main(String[] args) {
		
		Note nota1 = new Note("Db");
		
		System.out.println(nota1.getMidiValue());
		System.out.println(nota1.getFrequency());

	}
}
