package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //if(code == KeyEvent.VK_W){        // example bit of code, just to remember how to do key inputs without looking it up
        // }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
