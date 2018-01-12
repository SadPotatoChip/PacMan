package gameExe;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import java.util.HashSet;

import javafx.scene.Scene;

public class KeyboardListenerStuff {
	public HashSet<String> currentlyActiveKeys;
	
    public boolean isPressed(char c) {
        synchronized (KeyboardListenerStuff.class) {
        	if('w'==c)
        		return (currentlyActiveKeys.contains("W") || currentlyActiveKeys.contains("UP"));
        	if('s'==c)
        		return (currentlyActiveKeys.contains("S") || currentlyActiveKeys.contains("DOWN"));
        	if('a'==c)
        		return (currentlyActiveKeys.contains("A") || currentlyActiveKeys.contains("LEFT"));
        	if('d'==c)
        		return (currentlyActiveKeys.contains("D") || currentlyActiveKeys.contains("RIGHT"));
        	
        	System.out.println("read input error");
        	return false;
        }
    }

    public void setupKeyboardListener(Scene scene) {
    	currentlyActiveKeys = new HashSet<String>();
    	scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	    	@Override
	    	public void handle(KeyEvent event){
	    		currentlyActiveKeys.add(event.getCode().toString());
	    	}
    	});
    	scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
	    	@Override
	    	public void handle(KeyEvent event){
	    	    currentlyActiveKeys.remove(event.getCode().toString());
	    	}
    	});
    }
}
