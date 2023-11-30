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
        window.add(gamePanel);

        window.pack(); // cuases windo to be preffered size and layout

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();

    }
}
