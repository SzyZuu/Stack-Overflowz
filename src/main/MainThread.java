package main;

public class MainThread implements Runnable {       //todo main threat, pauses all other threads when mouse leaves screen

    Thread mainThread;
    GamePanel gp ;
    Grid grid ;

    public MainThread (GamePanel gamePanel, Grid grid){
        this.gp = gamePanel;
        this.grid = grid;
    }


    public void startMainThread() {
        mainThread = new Thread(this);
        mainThread.start();
    }

    public void run() {

        while (mainThread != null) {
            //if (gp.getMousePosition().x <= 0 || gp.getMousePosition().y <= 0 || gp.getMousePosition().x >= gp.screenWidth || gp.getMousePosition().y >= gp.screenHeight) {
            if (gp.getMousePosition() == null ) {
                try {
                    System.out.println("wait");
                    gp.gameThread.wait();
                    grid.gridThread.wait();
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                if(!gp.gameThread.isAlive()){
                    gp.gameThread.notify();
                }
                if(!grid.gridThread.isAlive()){
                    grid.gridThread.notify();
                }
            }
        }
    }
}