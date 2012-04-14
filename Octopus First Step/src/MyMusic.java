import examples.PlayableThings;
import octopus.*;

public class MyMusic {

	public static void main(String[] args) {
		try{
			Musician musician = new Musician();
			Harmony harmony;

			harmony = PlayableThings.getHarmonyToPlay();
			System.out.println(harmony); 
			musician.play(harmony);
			
		RhythmPattern ritmo = new RhythmPattern("ritmo1");
		Bar barM = new Bar(4,2);
		
		barM.addRhythmEvent(10, 2);
		barM.addRhythmEvent(RhythmConstants.HALF_NOTE, 3);
		
		ritmo.insertMark("beginning");
		ritmo.insertBar(barM);
		ritmo.insertReturn("beginning",3);	
		
			
		} catch (ChordNotationException e) {
			System.out.println("Ops...it seems that your musician is having troubles with the notes of the harmony: ");
		}

		catch (MusicPerformanceException ex) {
			System.out.println("Ops...it seems that your musician had troubles: " +  ex.getMessage());
		}catch (Exception e) {
			System.out.println("Ops...it seems that your musician had troubles: " +  e.getMessage());
		}finally{
			//Runtime.getRuntime().exit(0);
		}
	}

}
