package main;

import javax.swing.*;
import java.awt.*;

// Every developer has a tab open to Stack Overflowz
public class Main {
    public static void main (String[] args){
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Stack Overflowz");
        window.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        GamePanel gamePanel = new GamePanel();
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        //MainThread mainThread = new MainThread(gamePanel, grid);

        window.add(gamePanel, c);

        JLabel tutorialLabel = new JLabel();
        tutorialLabel.setBackground(new Color(27, 27, 27));
        tutorialLabel.setText("<html>Short tutorial:<br>somethingsomethingsomethingsomethingsomethingsomethingsomething</html>");
        c.gridwidth = 1;
        c.gridx = 4;
        c.gridy = 0;
        window.add(tutorialLabel, c);

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
