package octopus;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author Leandro Costalonga
 * @version 1.0
 *
 *@todo make it independent from the Java Sound. Back latter.
 */
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import octopus.communication.MusicalEventSequence;

/**
 * Music is a set of MusicalCompents scheduled in time. The music time line is
 * represented by the Music.Timeline Class which is responsible to index musical
 * components (harmony or melody) along in the time, expressed in musical terms.
 * <p> The figure bellow shows the internal representation of a Music composed 
 * with 3 Harmonies and 3 Melodies. Note that H1 and M3 start in the beginning 
 * of the music; hence the index is zero for both objects in the time line.
 *  The same does not happen to H2 and M1 that was scheduled to start in a latter 
 *  time requiring exclusive pointers for them in timeline table. </p>
 *  <center><img src="doc-files/music.jpg"></center>
 *
 */
public class Music implements Playable{
	//protected double refNote;
	protected double bpm;;

	protected Timeline timeline;


	public Music(){
		bpm = 120.0;
		timeline = new Timeline();

	}

	public void insertMusicalComponent( MusicalComponent component ){
		timeline.add(component,0.0);
	}
	
	public void insertMusicalComponent( MusicalComponent component, double startPos){
		timeline.add(component,startPos);
	}



	public void insertMusicalComponentSequencially( MusicalComponent[] componentArray,double startPos){
		for (int i=0; i< componentArray.length;i++){
			timeline.add(componentArray[i],startPos);
			startPos = componentArray[i].getDuration();
		}
	}


	public Timeline getTimeline(){
		return this.timeline;
	}


	public TimelineElement[] getTimelineElements(){
		return this.timeline.getTimelineElements();
	}


	@SuppressWarnings("unchecked")
	public class Timeline extends Vector{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		//Estrutura com hasbtable otimiza espaco e velocidade de acesso.
		Hashtable<Double, Integer> heap;

		Timeline(){
			heap = new Hashtable<Double, Integer>();
		}


		public void add(MusicalComponent component, double pos){
			Double ts = new Double(pos);
			Object objPosStack = heap.get(ts);
			TimelineElement te = null;
			if(objPosStack==null){
				te = new TimelineElement(pos);
				this.add(te);

				heap.put(ts,new Integer(this.size()-1));
			}else{
				te = (TimelineElement)get(((Integer)objPosStack).intValue());
			}
			te.add(component);
		}

		public TimelineElement[] getTimelineElements(){
			Vector sorted = new Vector(this);
			Collections.sort(sorted);
			return (TimelineElement[])sorted.toArray(new TimelineElement[0]);
		}



	}

	public  class TimelineElement extends Vector<Object> implements Comparable<Object>{

		private static final long serialVersionUID = 1L;

		public double timeStamp;
		TimelineElement(double timeStamp){
			super();
			this.timeStamp = timeStamp;
		}

		public int compareTo(Object obj2){
			if(timeStamp == ((TimelineElement)obj2).timeStamp){
				return 0;
			}else{
				if(timeStamp  < ((TimelineElement)obj2).timeStamp){
					return -1;
				}else{
					return +1;
				}
			}
		}
	}

	public MusicalEventSequence getMusicalEventSequence() {
		MusicalEventSequence musicMusicalEventSequence = new  MusicalEventSequence();
		Music.TimelineElement[] elements = getTimelineElements();
		for (int i=0;i<elements.length;i++){
			double startPos = elements[i].timeStamp;
			for (Enumeration<?> e = elements[i].elements() ; e.hasMoreElements() ;) {
				MusicalComponent musicalComponent = (MusicalComponent)e.nextElement();
				MusicalEventSequence musicalComponentSequence= musicalComponent.getMusicalEventSequence();
				musicalComponentSequence.delayEvents(startPos);
				musicMusicalEventSequence.addMusicalEventSequence(musicalComponentSequence);
			}
			musicMusicalEventSequence.setBpm(bpm);
		}
		return musicMusicalEventSequence;
	}

}
