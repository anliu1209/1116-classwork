import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Screen extends JPanel {
    // JPanel dimensions
    private static final int WIDTH = 650;
    private static final int HEIGHT = 500;

    private final Thread animationThread;       // Thread for animation
    private final Thread movementThread;        // Thread for movement
    private boolean playing = true;             // Flag to enable threads
    private String result = "";
    private MyHashTable<Location, GridObject> myGridTable;
    private Boat boat;

    // Constructor
    public Screen() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);

        // Initialize HashTable
        myGridTable = new MyHashTable<Location, GridObject>();

        GridObject sand = new GridObject("sand");
        GridObject dirt = new GridObject("dirt");
        GridObject water = new GridObject("water");
        GridObject grass = new GridObject("grass");
        GridObject boulder = new GridObject("boulder");
        GridObject tree = new GridObject("tree");

        // Row 0
        myGridTable.put(new Location(0, 0), water);
        myGridTable.put(new Location(0, 1), water);
        myGridTable.put(new Location(0, 2), sand);
        myGridTable.put(new Location(0, 3), grass);
        myGridTable.put(new Location(0, 3), tree);
        myGridTable.put(new Location(0, 4), grass);
        myGridTable.put(new Location(0, 5), dirt);
        myGridTable.put(new Location(0, 5), boulder);
        myGridTable.put(new Location(0, 6), sand);
        myGridTable.put(new Location(0, 7), water);
        myGridTable.put(new Location(0, 8), water);
        myGridTable.put(new Location(0, 9), water);

        // Row 1
        myGridTable.put(new Location(1, 0), sand);
        myGridTable.put(new Location(1, 1), water);
        myGridTable.put(new Location(1, 2), water);
        myGridTable.put(new Location(1, 3), sand);
        myGridTable.put(new Location(1, 4), grass);
        myGridTable.put(new Location(1, 4), tree);
        myGridTable.put(new Location(1, 5), dirt);
        myGridTable.put(new Location(1, 6), sand);
        myGridTable.put(new Location(1, 7), water);
        myGridTable.put(new Location(1, 8), dirt);
        myGridTable.put(new Location(1, 8), boulder);
        myGridTable.put(new Location(1, 9), water);

        // Row 2
        myGridTable.put(new Location(2, 0), grass);
        myGridTable.put(new Location(2, 1), sand);
        myGridTable.put(new Location(2, 2), water);
        myGridTable.put(new Location(2, 3), water);
        myGridTable.put(new Location(2, 4), sand);
        myGridTable.put(new Location(2, 5), grass);
        myGridTable.put(new Location(2, 6), water);
        myGridTable.put(new Location(2, 7), water);
        myGridTable.put(new Location(2, 8), sand);
        myGridTable.put(new Location(2, 9), grass);

        // Row 3
        myGridTable.put(new Location(3, 0), dirt);
        myGridTable.put(new Location(3, 0), boulder);
        myGridTable.put(new Location(3, 1), grass);
        myGridTable.put(new Location(3, 2), sand);
        myGridTable.put(new Location(3, 3), water);
        myGridTable.put(new Location(3, 4), water);
        myGridTable.put(new Location(3, 5), water);
        myGridTable.put(new Location(3, 6), water);
        myGridTable.put(new Location(3, 7), sand);
        myGridTable.put(new Location(3, 8), grass);
        myGridTable.put(new Location(3, 8), tree);
        myGridTable.put(new Location(3, 9), dirt);

        // Row 4
        myGridTable.put(new Location(4, 0), grass);
        myGridTable.put(new Location(4, 0), tree);
        myGridTable.put(new Location(4, 1), dirt);
        myGridTable.put(new Location(4, 2), water);
        myGridTable.put(new Location(4, 3), water);
        myGridTable.put(new Location(4, 4), sand);
        myGridTable.put(new Location(4, 5), water);
        myGridTable.put(new Location(4, 6), water);
        myGridTable.put(new Location(4, 7), water);
        myGridTable.put(new Location(4, 8), sand);
        myGridTable.put(new Location(4, 9), grass);

        // Row 5
        myGridTable.put(new Location(5, 0), sand);
        myGridTable.put(new Location(5, 1), water);
        myGridTable.put(new Location(5, 2), water);
        myGridTable.put(new Location(5, 3), sand);
        myGridTable.put(new Location(5, 4), grass);
        myGridTable.put(new Location(5, 5), dirt);
        myGridTable.put(new Location(5, 5), boulder);
        myGridTable.put(new Location(5, 6), sand);
        myGridTable.put(new Location(5, 7), water);
        myGridTable.put(new Location(5, 8), water);
        myGridTable.put(new Location(5, 9), sand);

        // Row 6
        myGridTable.put(new Location(6, 0), water);
        myGridTable.put(new Location(6, 1), water);
        myGridTable.put(new Location(6, 2), sand);
        myGridTable.put(new Location(6, 3), grass);
        myGridTable.put(new Location(6, 3), tree);
        myGridTable.put(new Location(6, 4), dirt);
        myGridTable.put(new Location(6, 5), grass);
        myGridTable.put(new Location(6, 6), water);
        myGridTable.put(new Location(6, 7), water);
        myGridTable.put(new Location(6, 8), water);
        myGridTable.put(new Location(6, 9), grass);
        myGridTable.put(new Location(6, 9), tree);

        // Row 7
        myGridTable.put(new Location(7, 0), water);
        myGridTable.put(new Location(7, 1), sand);
        myGridTable.put(new Location(7, 2), dirt);
        myGridTable.put(new Location(7, 2), boulder);
        myGridTable.put(new Location(7, 3), grass);
        myGridTable.put(new Location(7, 4), sand);
        myGridTable.put(new Location(7, 5), water);
        myGridTable.put(new Location(7, 6), water);
        myGridTable.put(new Location(7, 7), sand);
        myGridTable.put(new Location(7, 8), water);
        myGridTable.put(new Location(7, 9), water);

        // Row 8
        myGridTable.put(new Location(8, 0), water);
        myGridTable.put(new Location(8, 1), water);
        myGridTable.put(new Location(8, 2), water);
        myGridTable.put(new Location(8, 3), water);
        myGridTable.put(new Location(8, 4), water);
        myGridTable.put(new Location(8, 5), water);
        myGridTable.put(new Location(8, 6), sand);
        myGridTable.put(new Location(8, 7), dirt);
        myGridTable.put(new Location(8, 7), boulder);
        myGridTable.put(new Location(8, 8), sand);
        myGridTable.put(new Location(8, 9), water);

        // Row 9
        myGridTable.put(new Location(9, 0), sand);
        myGridTable.put(new Location(9, 1), grass);
        myGridTable.put(new Location(9, 1), tree);
        myGridTable.put(new Location(9, 2), sand);
        myGridTable.put(new Location(9, 3), dirt);
        myGridTable.put(new Location(9, 4), grass);
        myGridTable.put(new Location(9, 5), water);
        myGridTable.put(new Location(9, 6), water);
        myGridTable.put(new Location(9, 7), water);
        myGridTable.put(new Location(9, 8), water);
        myGridTable.put(new Location(9, 9), water);

        // Initialize boat 
        boat = new Boat(4, 5);

        // Movement Thread: Calculates new location for boat
        movementThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (playing) {
                    // Boat chooses adjacent tile from grid
                    boat.chooseNextWater(myGridTable);

                    try {
                        // Every 1000 ms
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        });

        // Animation Thread: Repaints screen and moves boat
        animationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (playing) {
                    // Boat slightly moves to new location
                    boat.updateAnimation();
                    repaint();

                    try {
                        // Every 20 ms
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        });

        movementThread.start();
        animationThread.start();
    }

    // Stops both threads when the game ends
    public void stopThreads() {
        playing = false;
        if (animationThread != null) {
            animationThread.interrupt();
        }
        if (movementThread != null) {
            movementThread.interrupt();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        // Draw all GridObjects from the hash table
        for(int row=0; row<10; row+=1){
            for(int col=0; col<10; col+=1){
                // Creates location for each possible space to get GridObject info for painting
                Location key = new Location(row, col);
                DLList<GridObject> gridObjectsList = myGridTable.get( key );
                if(gridObjectsList != null){
                    for(int item=0; item<gridObjectsList.size(); item+=1){
                        gridObjectsList.get(item).drawMe(g, row, col);
                    }
                }
            }
        }

        // Draw the boat on the water tiles
        if (boat != null) {
            boat.drawMe(g);
        }
    }
}