package nip;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * The Sound class provides methods for playing audio clips with an .au file extension.
 * Students are advised that headphones are generally required in university computer centers.
 * 
 * @author Kenneth J. Goldman<BR>
 * Created Jul 5, 2005
 */
public class Sound {
	private static final HashMap<String,AudioClip> sounds = new HashMap<String,AudioClip>();
	
	/**
	 * Loads, but does not play, an audio clip from the given file
	 * 
	 * @param filename the name of the audio file
	 * @return the loaded AudioClip
	 */
	public static AudioClip getSound(String filename) {
		if (!sounds.containsKey(filename)) {
			AudioClip ac = null;
			Applet applet = NIP.getApplet();
			try {
				if (applet != null) {
					try {
						ac = applet.getAudioClip(applet.getDocumentBase(),filename);
					} catch (Exception e) {
						//System.err.println(e);
					}
				}
				if (ac == null)	
					ac = java.applet.Applet.newAudioClip(new URL("file://"+filename));
			} catch (MalformedURLException e) {
				System.err.println("Sound file not found: "+ filename);
			}
			if (ac == null)
				throw new IllegalArgumentException("Sound file " + filename + " not found.");
			sounds.put(filename,ac);
		}
		return sounds.get(filename);
	}
	
	/**
	 * Plays the audio clip from the given file, loading the audio clip if necessary.
	 * (If the audio clip has been previously loaded, it will play the clip that has already been loaded.)
	 * @param filename the name of the audio file
	 */
	public static void play(String filename) {
		AudioClip ac = getSound(filename);
		if (ac != null)
			ac.play();
	}

	/**
	 * Plays the audio clip repeatedly from the given file, loading the audio clip if necessary.
	 * (If the audio clip has been previously loaded, it will play the clip that has already been loaded.)
	 * @param filename the name of the audio file
	 */
	public static void loop(String filename) {
		AudioClip ac = getSound(filename);
		if (ac != null)
			ac.loop();
	}

	/**
	 * Stops playing the named audio clip, if it is currently playing.
	 * @param filename the name of the audio file
	 */
	public static void stop(String filename) {
		AudioClip ac = getSound(filename);
		if (ac != null)
			ac.stop();
	}
	
	public static void main(String[] args) {
		play("/Users/kjg/eclipse/kjg-workspace/YOPS/beep.au");
	}
}
