package evolutionSimulator.Logic;
import evolutionSimulator.Models.SingleCell;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;

import java.util.Random;

public class Basic extends Thread {
    public Basic(SingleCell[][] inputMap) {
        //MainWindow.mainGrid
        //start();
        new Thread(task).start();
    }
    Task task = new Task<Void>() {
        @Override
        protected Void call() {
            return null;
        }

        @Override public void run() {
            Random rand = new Random();
            Color[] colors = {Color.WHITE, Color.YELLOW, Color.GRAY, Color.PURPLE};
            //MainWindow.cells[1][1].setFill(Color.PINK);
            for(int i=0; i<8; i++) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int nC = rand.nextInt(4);
                int nC1 = rand.nextInt(50);
                int nC2 = rand.nextInt(50);
                //MainWindow.cells[nC1][nC2].setFill(colors[nC]);
            }

        }
    };
/*    @Override
    public synchronized void start() {
        super.start();
        Random rand = new Random();
        Color[] colors = {Color.WHITE, Color.YELLOW, Color.GRAY, Color.PURPLE};
        MainWindow.cells[1][1].setFill(Color.PINK);
        for(int i=0; i<10; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int nC = rand.nextInt(4);
            MainWindow.cells[1][1].setFill(colors[nC]);
        }
    }*/
}
