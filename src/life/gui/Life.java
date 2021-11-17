package life.gui;

import static org.lwjgl.glfw.GLFW.*;

public class Life {
	private GUI gui;
	
	public Life(GUI gui) {
		this.gui = gui;
	}

	public void run() {
		Window window = gui.createWindow(640, 480, "Hello World!");

		window.attachKeyCallback((key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE ) {
				window.close();
			}
		});
		
		window.center();

		window.show();
		
		gui.loop(()->!window.closed(), ()->{
			window.render();
		});
		
		window.destroy();
		
		gui.terminate();
	}

}