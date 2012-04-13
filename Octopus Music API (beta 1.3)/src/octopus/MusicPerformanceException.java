package octopus;

/**
 * Thrown when there is a problem in the musical performance.
 * @author lcostalonga
 *
 */
public class MusicPerformanceException
extends Exception {

	private static final long serialVersionUID = 1L;
	Musician musician;
	Playable playableObject;

	public MusicPerformanceException(String message) {
		super(message);

	}

	public MusicPerformanceException(String message, Musician musician, Playable playableObject) {
		super(message);
		this.musician = musician;
		this.playableObject = playableObject;
	}

	public MusicPerformanceException(String message, Throwable cause) {
		super(message, cause);
	}

	public MusicPerformanceException(Throwable cause) {
		super(cause);
	}
}
