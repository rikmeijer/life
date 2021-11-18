package life;

import com.badlogic.gdx.backends.lwjgl3.*;

import life.gui.Life;

public class DesktopLauncher {

	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Life");
		config.setWindowedMode(800, 480);
		new Lwjgl3Application(new Life(), config);
	}
}