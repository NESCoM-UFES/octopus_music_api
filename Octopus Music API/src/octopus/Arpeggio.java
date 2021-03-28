package octopus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Vector;

import octopus.communication.MusicalEventSequence;

/**
 * Arpeggio is a set of <code>{@link RhythmPattern}</code> played simultaneously. It is used to 
 * spread the notes of the Chord throughout its overall duration (voicing).  
 *
 *<p>Inside the Arpeggio, the RhythmPatterns (called voices) are organised in vertical parallel lines, as 
 * shown in the Figure bellow.
 * The lowest voice (index 0) is linked to <code>ChordNote</code> with the lowest pitch, the second lower to the
 * second lower <code>ChordNote</code> pitch and so on. </p>
 * <center><img src="doc-files/arpeggio.jpg"></center>
 * <p><a href="../examples/ArpeggioExample.java"> Code example: A fast arpeggio played by a <code> Musician</code>.</a></p>

 *
 * @see RhythmPattern
 * @see Chord
 *
 * @author Leandro Costalonga
 * @version 1.1
 */
public class Arpeggio  implements Serializable,Playable,RhythmConstants{

	private static final long serialVersionUID = 1L;

	protected Vector<RhythmPattern> voices = new Vector<RhythmPattern>();

	protected String name = "unknown";
	float bpm = 120; //Overall bpm

	//Stretch the arpeggio duration to  match a certain duration;
	private boolean timeStretch = true; 

	// Just for interenal developing purposes. 
	
	public Arpeggio(){
		voices = new Vector<RhythmPattern>();
	}
	
	public Arpeggio(RhythmPattern[] rhythmPattern){
		voices = new Vector<RhythmPattern>(Arrays.asList(rhythmPattern));
	}


	/**
	 * Creates an "named" Arpeggio with the specified polyphony. 
	 * @param name name of the arpeggio;
	 * @param polyphony number of voices of the arpeggio; 
	 */
	public Arpeggio(String name, int polyphony) {
		this.name = name;
		for (int i=0;i<polyphony;i++){
			this.voices.add(i, new RhythmPattern());
		}
	}

	/**
	 * Creates an "unknown" named Arpeggio with the specified polyphony. 
	 * @param polyphony number of voices of the arpeggio; 
	 */ 
	public Arpeggio(int polyphony) {
		for (int i=0;i<polyphony;i++){
			this.voices.add(i, new RhythmPattern());
		}
	}

	/**
	 * Creates an arpeggio with a single rhythm note per bar with the specified
	 * duration. All the events are played simultaneously.
	 * @param polyphony number of voices of the arpeggio; 
	 * @param duration duration of rhythm note; 
	 */
	public Arpeggio(int polyphony, double duration) {
		for (int i=0;i<polyphony;i++){
			RhythmPattern rhythmPattern =  new RhythmPattern();
			Bar bar = new Bar(1,4);
			bar.addRhythmEvent(duration,RhythmConstants.RHYTHM_EVENT_NOTE);
			rhythmPattern.insertBar(bar);
			this.voices.add(i, rhythmPattern);
		}
	}

	/**
	 * Merges (in sequence) several arpeggios into a new one . Used to create complex rhythmic structures. 
	 * @param arpeggios array of <code> Arpeggio </code>
	 */
	public Arpeggio(Arpeggio[] arpeggios){
		this.name = "Arpeggio merge: ";
		//For each Arpeggio
		double startTime = 0.0;

		for (int i=0;i<arpeggios.length;i++){
			this.name += arpeggios[i].getName();

			//For each voice of the Arpeggio
			for(int j = 0; j <arpeggios[i].getPolyphony(); j++){
				if(j > voices.size()){
					this.voices.add( new RhythmPattern());
				}

				//Merging the old Aperggios data with the new one; 
				RhythmPattern voiceOldArpeggio = arpeggios[i].getVoice(j); 
				RhythmPattern voiceNewArpeggio = (RhythmPattern)voices.get(j);

				double voiceDuration = voiceNewArpeggio.getDuration();
				if(voiceDuration < startTime){
					Bar mutedBar = new Bar(1, 4);
					double durationMutedBar = startTime - voiceDuration;
					mutedBar.addRhythmEvent(durationMutedBar, RhythmConstants.RHYTHM_EVENT_NOTE);
					voiceNewArpeggio.insertBar(mutedBar);

				}

				voiceNewArpeggio.bars.addAll(voiceOldArpeggio.bars); //Copy Bars and Marks
				voiceNewArpeggio.repetitionMarks.putAll(voiceOldArpeggio.repetitionMarks); //Copy the ReturnPoints

			}
			startTime +=  arpeggios[i].getDuration();
		}

	}
	//For next version
	/* protected Arpeggio(Sequence sequence) {
  OctopusReceiver r = new OctopusReceiver();
  float duration = 0;
  Track[]  t = sequence.getTracks();
  //polyphony = t.length;
   for(int h=0; h< t.length;h++){
     Bar bar = new Bar(); //verificar relacao resolution = beats or refNote
     this.voices.add(h, new RhythmPattern());
     float nTempo = r.convertTempo(sequence.getMicrosecondLength()); //errado
   //  bpm = (float) (Math.round(nTempo*100)/100.0f);
     int i=0;
     while (i< t[h].size()){
       MidiEvent midiEvent = t[h].get(i);
       MidiMessage midiMessage = midiEvent.getMessage();

       if(midiMessage instanceof ShortMessage){
         MidiEvent nextEvent = t[h].get(i+1);
         float durationTicks =nextEvent.getTick() - midiEvent.getTick();
         duration = (durationTicks/sequence.getResolution())/4;

        if(midiMessage.getStatus()==ShortMessage.NOTE_ON){
         byte status = ((ShortMessage)midiMessage).getData2()>0?Bar.RHYTHM_EVENT_NOTE:Bar.RHYTHM_EVENT_NOTE;
          bar.addRhythmEvent(duration, status);
        }
       }

       i++;
     }
     insertBar(bar,h);
   }
}*/

	/**
	 * Insert a Bar into a particular voice (index) of the Arpeggio.
	 * @param voice index starting in 0. Represents a voice of the chord.
	 * Lower the number is, lower the note pitch.
	 */
	public void insertBar(Bar bar, int voice){
		((RhythmPattern)voices.get(voice)).insertBar(bar);
	}

	
	/**
	 * Insert the referred  rhythmPattern in the next free slot.
	 * @return the numer of the voice in which the RhythmPattern was inserted.
	 * @param rhythmPattern
	 * 
	 */
	public int insertRhythmPattern(RhythmPattern rhythmPattern){
		voices.add(rhythmPattern);
		return voices.size() -1;
	}

	/**
	 * Replaces the RythmPAtter referred by the voice with  the informed one.
	 * @param rhythmPattern
	 * @voice voice (number);
	 * 
	 */
	public void replaceRhythmPattern(RhythmPattern rhythmPattern, int voice){
		voices.remove(voice);
		voices.add(voice, rhythmPattern);		
	}
	
	
	/**
	 * Assign an array of RhythmPattern as  
	 * @param rhythmPattern
	 * @deprecated
	 */
	public void setVoices(RhythmPattern[] rhythmPattern){
		voices = new Vector<RhythmPattern>(Arrays.asList(rhythmPattern));
	}


	/**
	 * Returns a an array of all <code>RhythmEvents</code>
	 * sorted by timing and voice.
	 */
	//@SuppressWarnings("unchecked")
	public SortableRhythmEvent[] getSortableRhythmEvents(){
		Vector<SortableRhythmEvent> rhythmEvents = new Vector<SortableRhythmEvent>();

		double resolution = 4.0;
		for(int x=0;x<voices.size();x++){
			double time = 0;
			RhythmPattern rp =(RhythmPattern)voices.get(x);
			for (int e = 0; e < rp.bars.size(); e++) {
				if(rp.bars.get(e) instanceof Bar){
					Bar bar = (Bar) rp.bars.get(e);
					for (int i = 0; i < bar.rhythmEvents.size(); i++) {
						Bar.RhythmEvent rhythmEvent = (Bar.RhythmEvent) bar.rhythmEvents.get(i);
						double value = rhythmEvent.duration;
						double duration = (value * 4.0) * resolution;
						//double duration = 4;
						boolean tie = rhythmEvent.tie;
						int type = rhythmEvent.type;
						if ( (tie) && (i < bar.rhythmEvents.size() - 1)) {
							i++;
							value = rhythmEvent.duration;
							duration = (value * 4.0) * resolution;
							duration += rhythmEvent.duration *
							resolution;
						}

						if(type==1){
							rhythmEvents.add(new SortableRhythmEvent(time,x,bar,rhythmEvent));
						}
						time += duration;
					}
				}
			}
		}

		Collections.sort(rhythmEvents);
		return (SortableRhythmEvent[])rhythmEvents.toArray(new SortableRhythmEvent[0]);
	}


	public RhythmPattern[] getVoices(){
		return (RhythmPattern[])voices.toArray(new RhythmPattern[0]);
	}

	public RhythmPattern getVoice(int voice){
		return (RhythmPattern)voices.get(voice);
	}

	public void insertMark(String name, int voice){
		((RhythmPattern)voices.get(voice)).insertMark(name);
	}


	public String toString(){
		String r = "";
		for(int s=0; s<this.getPolyphony();s++){
			r+=(s + "================== \n");
			RhythmPattern rp = ((RhythmPattern)voices.get(s));
			if(rp.bars.size() ==0){
				// if(bars.size()>0){
				// super.print();
				//}
			}else{
				r+=((RhythmPattern) voices.get(s)).toString();
			}
		}
		return r;
	}



	/**
	 * The arpeggio duration is equal to the longest voice duration that composes it.
	 */
	public double getDuration(){
		double maxDuration = 0.0;
		for(int i = 0; i < voices.size(); i++){
			double tempDuration = ((RhythmPattern)voices.get(i)).getDuration();
			if(tempDuration > maxDuration){
				maxDuration = tempDuration;
			}
		}
		return maxDuration;
	}

	/**
	 * Returns the number of notes that are played simultaneously. Note that
*	 * simultaneous attacks is different than polyphony of the arpeggio(could be
	 * played any time within a certain duration).
	 */
	public int getSimultaneousAttacks(){
		Hashtable<Long, Integer> ht = new Hashtable<Long, Integer>();

		int simultaneousAttacks = 0;
		double resolution = 4.0;
		for(int i=0;i<voices.size();i++){
			int se=0;
			long time = 0;
			RhythmPattern rp =(RhythmPattern)voices.get(i);
			for (int j = 0; j < rp.bars.size(); j++) {
				if (! (rp.bars.get(j) instanceof Bar)) {
					if (rp.bars.get(j) instanceof String) {
						se = j;
					}
					if (rp.bars.get(j) instanceof RhythmPattern.ReturnPoint) {
						if ( ( (RhythmPattern.ReturnPoint) rp.bars.get(j)).counter > 0) {
							( (RhythmPattern.ReturnPoint) rp.bars.get(j)).decrease();
							j = se + 1;
						}
					}
				}
				else {
					Bar bar = (Bar) rp.bars.get(j);
					for (int k = 0; k < bar.rhythmEvents.size(); k++) {
						Bar.RhythmEvent rhythmEvent = (Bar.RhythmEvent) bar.rhythmEvents.get(k);
						double value = rhythmEvent.duration;
						double duration = (value * 4.0) * resolution;
						//double duration = 4;
						boolean tie = rhythmEvent.tie;
						int type = rhythmEvent.type;
						if ( (tie) && (k < bar.rhythmEvents.size() - 1)) {
							k++;
							value = rhythmEvent.duration;
							duration = (value * 4.0) * resolution;
							duration += rhythmEvent.duration *
							resolution;
						}

						if(type==1){
							Long key = new Long(time);
							if (ht.containsKey(key)) {
								Integer repetitions = (Integer)ht.get(key);
								repetitions = new Integer(repetitions.intValue() + 1);
								ht.put( (key), repetitions);
								if(repetitions.intValue()>simultaneousAttacks){
									simultaneousAttacks = repetitions.intValue();
								}
							}else{
								ht.put( (key), new Integer(1));
							}
						}
						time += duration;
					}
				}
			}
		}

		return simultaneousAttacks;
	}


	public static Arpeggio getDemoArpeggio(int nVoices){
		Arpeggio arpeggio = new Arpeggio(nVoices);
		arpeggio.setName("Demo Arpeggio:" + nVoices + " voices");
		Bar[] bars = new Bar[nVoices];
		for(int i = 0; i<nVoices; i++){
			bars[i] = new Bar(nVoices,16);
			bars[i].addSingleRhythmEvent(RhythmConstants.SIXTEENTH_NOTE,RhythmConstants.RHYTHM_EVENT_NOTE,i);
			arpeggio.insertBar(bars[i],i);
		}

		return arpeggio;
	}

	/**
	 * Serialise the arpeggio object in the specified file. In the future this method
	 * will probable be replaced by a XML store one.
	 *
	 */
	public  void store(File file) throws
	FileNotFoundException, IOException {

		if (!file.getName().endsWith(".prp")) {
			file = new File(file.getPath() + ".prp");
		}

		if (file != null) {
			if (!file.getName().endsWith("prp")) {
				file = new File(file.getPath() + ".prp");
			}
			FileOutputStream fOut = new FileOutputStream(file);
			ObjectOutput out = new ObjectOutputStream(fOut);
			out.writeObject(this);
			out.flush();
			out.close();
		}


	}
	/**
	 * Restore an  arpeggio object from the specified file. In the future this method
	 * will probable be replaced by a a XML loader.
	 */
	public static Arpeggio load(File file) throws FileNotFoundException,
	IOException, ClassNotFoundException {
		Arpeggio rhythmPattern = null;;
		if (file != null) {
			FileInputStream fIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fIn);
			rhythmPattern = (Arpeggio) in.readObject();

			in.close();

		}

		return rhythmPattern;

	}




	public int getPolyphony() {
		return voices.size();
	}

	public String getName() {
		return name;
	}

	/**
	 * This BPM can be overridden by the Music BPM.
	 */
	public void setBpm(float bpm) {
		this.bpm = bpm;
	}

	public float getBpm() {
		return bpm;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * If true, make the size of the arpeggio equals to the respective rhythm event
	 * duration. If false, trims the events longer then the rhythm event duration.
	 */
	public void setTimeStratch(boolean timeStratch) {
		this.timeStretch = timeStratch;
	}

	public boolean isTimeStratch() {
		return timeStretch;
	}

	/**
	 * Class used to get all the RhythmEvents inside the Arpeggio and sort them
	 * by execution time and voice.
	 */
	public class SortableRhythmEvent implements Comparable<Object> {
		public double time;
		public int voice;
		public Bar.RhythmEvent rhythmEvent;
		public Bar bar;

		public SortableRhythmEvent(double time, int voice, Bar bar,Bar.RhythmEvent re) {
			this.time = time;
			this.voice = voice;
			this.bar = bar;
			this.rhythmEvent = re;
		}

		public int compareTo(Object obj2) {
			SortableRhythmEvent o = (SortableRhythmEvent) obj2;
			if (o.time == time) {
				if (voice < o.voice) {
					return -1;
				}
				else {
					if (voice > o.voice) {
						return 1;
					}
					return 0;
				}
			}
			else {
				if (o.time < time) {
					return 1;
				}
				else {
					return -1;
				}
			}
		}
		@Override
		public String toString(){
			return time + " " + voice + " " +rhythmEvent.duration;
		}
	}

	/**
	 * Return all the Bar.RythmEvents inside the RhythmPattern in order of execution. 
	 * @return  the Bar.RythmEvents inside the RhythmPattern
	 */
	public Bar.RhythmEvent[][] getRhythmEvents(double duration){
		double timeAdjustiveFactor = 0.0; 
		Vector<Bar.RhythmEvent[]> arpeggioRhythmEvents = new Vector<Bar.RhythmEvent[]>();
		for (int i = 0; i < voices.size(); i++) {
			RhythmPattern voice = voices.get(i);		
			Bar.RhythmEvent[]  voiceRhythmEvents = voice.getRhythmEvents(true); //tie notes merged
			double sumRhythmEventDuration = 0.0;	
			int indexRhythmEvents = 0;
			while ((sumRhythmEventDuration < duration) &&
					(indexRhythmEvents <voiceRhythmEvents.length)){ //Not sure about that..just a quick ammend.
				if (isTimeStratch()) {
					timeAdjustiveFactor = timeAdjustiveFactor==0?(duration/getDuration()):timeAdjustiveFactor;
					voiceRhythmEvents[indexRhythmEvents].duration = voiceRhythmEvents[indexRhythmEvents].duration  * timeAdjustiveFactor;

				}else{
					if (voiceRhythmEvents[indexRhythmEvents].duration > duration){
						voiceRhythmEvents[indexRhythmEvents].duration = duration;
					}
				}
				sumRhythmEventDuration+=voiceRhythmEvents[indexRhythmEvents].duration;
				indexRhythmEvents++;
			}
			arpeggioRhythmEvents.add(voiceRhythmEvents);
		}
		return (Bar.RhythmEvent[][])arpeggioRhythmEvents.toArray(new Bar.RhythmEvent[0][0]);

	}




	public MusicalEventSequence getMusicalEventSequence() {
		MusicalEventSequence p =new MusicalEventSequence();

		for(int i=0;i<voices.size();i++){

			RhythmPattern rhythmPattern =(RhythmPattern)voices.get(i);

			//Note note  =rhythmPattern.pitchRhythmNote;
			try{
				rhythmPattern.pitchRhythmNote = Notes.getNote(rhythmPattern.pitchRhythmNote,(i * 5));
			}catch(NoteException ex){
				ex.printStackTrace();
			}

			MusicalEventSequence voiceMusicalSequence = rhythmPattern.getMusicalEventSequence();
			p.addMusicalEventSequence(voiceMusicalSequence);


		}
		return p;
	}



}
