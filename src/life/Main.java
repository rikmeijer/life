package life;

import life.gui.GUI;
import life.gui.Life;

public class Main {

	public static void main(String[] args) {

		Life app = new Life(new GUI());
		app.run();

	}
}