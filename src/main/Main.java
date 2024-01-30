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

        GamePanel gamePanel = new GamePanel();              //initialize game panel
        c.gridwidth = 3;                                    //3 grids wide
        c.gridx = 0;                                        //first grid
        c.gridy = 0;
        //MainThread mainThread = new MainThread(gamePanel, grid);

        window.add(gamePanel, c);                           //add game panel ot the window, with grid constraints

        JPanel tutorialPanel = new JPanel();                //new panel for tutorial
        //tutorialPanel.setBackground(Color.BLACK);         //change background color, but doesnt work
        c.gridwidth = 1;                                    //only 1 grid wide
        c.gridx = 4;                                        //4th column first row
        c.gridy = 0;
        window.add(tutorialPanel, c);                       //add tutorial panel, with c

        JLabel tutorialLabel = new JLabel();
        tutorialLabel.setText("<html>Short tutorial:<br>somethingsomethingsomethingsomethingsomethingsomethingsomething</html>");   //use html in label for easier formatting (could've made a separate file for that but too much work and not that necessary)
        tutorialPanel.add(tutorialLabel);                   //ad the text to the tutorial


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
