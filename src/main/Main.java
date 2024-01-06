package main;

import javax.swing.*;

// Every developer has a tab open to Stack Overflowz
public class Main {
    public static void main (String[] args){
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Stack Overflowz");

        GamePanel gamePanel = new GamePanel();
        //MainThread mainThread = new MainThread(gamePanel, grid);

        window.add(gamePanel);
        window.pack(); // causes window to be preferred size and layout
        window.setLocationRelativeTo(null);
        //window.setExtendedState(JFrame.MAXIMIZED_BOTH);       for full screen
        //window.setUndecorated(true);
        window.setVisible(true);

        gamePanel.startingCards();
        //mainThread.startMainThread();
        gamePanel.startGameThread();
        //grid.startGridThread();
    }
}
