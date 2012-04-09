package julia;

import javax.swing.JApplet;
import nip.NIP;

public class MyApplet extends JApplet {
	
	//  runs when the applet is started
	public void init() {
		NIP.setApplet(this);
		Main.main(null);
	}
	
	//  runs when the applet is shut down
	public void stop() {
		Main.nip.dispose();
	}

}
