package octopus.repl;

import octopus.Bar;
import octopus.RhythmPattern;


public class RhythmREPL {
	
	
	
	
	public static Mark mark(String name) {
		return new Mark(name);
	}
	
	public static ReturnPoint returnTo(String markName, int repetitions) {
		return new ReturnPoint(markName,repetitions);
	}
	
	
	public RhythmPattern random(int nNotes) {
		return RhythmPattern.getRandomRhythmPattern(nNotes);
	}
	
	public RhythmPattern constant(double rhythmPatterDuration, double noteDuration) {
		return RhythmPattern.getConstantRhythmPattern(rhythmPatterDuration, noteDuration);
	}
	
	public RhythmPattern constant(int nNotes, double noteDuration) {
		return RhythmPattern.getConstantRhythmPattern(nNotes,noteDuration);
	}
	
	
	public static class ReturnPoint implements RhythmPattern.Things{
		public String markName;
		public int repetitions;
		
		ReturnPoint(String markName, int repetitions){
			this.markName = markName;
			this.repetitions = repetitions;
		}
	}
	
	public static class Mark implements RhythmPattern.Things{
		public String name;
		
		Mark(String name){
			this.name= name;
		}
		
	}
	
	

}
