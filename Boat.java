import java.awt.Color;
import java.awt.Graphics;

public class Boat {
    private static final int SIZE = 46;
    private int row;              // Current grid row
    private int col;              // Current grid column
    private int targetRow;        // Destination grid row chosen by movementThread
    private int targetCol;        // Destination grid column chosen by movementThread
    private double startX;        // Starting pixel X for the current 1-second move
    private double startY;        // Starting pixel Y for the current 1-second move
    private double x;             // Current pixel X position for smooth animation
    private double y;             // Current pixel Y position for smooth animation
    private long moveStartTime;   // Timestamp (ns) when the current move began

    private Color hullBrown = new Color(122, 73, 40);
    private Color mastBrown = new Color(78, 46, 24);
    private Color sailWhite = new Color(245, 245, 245);
    private Color flagRed = new Color(214, 69, 65);
    private Color wakeWhite = new Color(220, 242, 245);

    public Boat(int startRow, int startCol) {
        this.row = startRow;
        this.col = startCol;
        this.targetRow = startRow;
        this.targetCol = startCol;
        this.x = startCol * 50.0;
        this.y = startRow * 50.0;
        this.startX = this.x;
        this.startY = this.y;
        this.moveStartTime = System.nanoTime();
    }

    // Called once per second by movementThread to pick an adjacent water cell
    public synchronized void chooseNextWater(MyHashTable<Location, GridObject> grid) {
        // Commit logical grid coordinates and anchor the next glide from current pixels (no snap)
        row = targetRow;
        col = targetCol;
        startX = x;
        startY = y;

        // Check up, down, left, and right neighbors for water tiles
        int[][] directions = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
        int[][] validMoves = new int[4][2];
        int count = 0;

        for (int i = 0; i < directions.length; i++) {
            int nextRow = row + directions[i][0];
            int nextCol = col + directions[i][1];

            if (nextRow >= 0 && nextRow < 10 && nextCol >= 0 && nextCol < 10) {
                DLList<GridObject> cellObjects = grid.get(new Location(nextRow, nextCol));
                if (cellObjects != null && cellObjects.size() > 0) {
                    if (cellObjects.get(0).getName().equalsIgnoreCase("water")) {
                        validMoves[count][0] = nextRow;
                        validMoves[count][1] = nextCol;
                        count++;
                    }
                }
            }
        }

        // Pick a random valid adjacent water cell and reset the 1-second timer
        if (count > 0) {
            int randomIndex = (int) (Math.random() * count);
            targetRow = validMoves[randomIndex][0];
            targetCol = validMoves[randomIndex][1];
        }
        moveStartTime = System.nanoTime();
    }

    // Called each frame by animationThread using real elapsed time (0.0 to 1.0 over 1 second)
    public synchronized void updateAnimation() {
        double progress = (System.nanoTime() - moveStartTime) / 1_000_000_000.0;
        if (progress > 1.0) {
            progress = 1.0;
        }

        double targetX = targetCol * 50.0;
        double targetY = targetRow * 50.0;

        x = startX + (targetX - startX) * progress;
        y = startY + (targetY - startY) * progress;
    }

    // Synchronized with movement/animation updates so coordinates stay consistent mid-draw
    public synchronized void drawMe(Graphics g) {
        int px = (int) Math.round(x);
        int py = (int) Math.round(y);

        // Water underneath the hull
        g.setColor(wakeWhite);
        g.drawLine(px + 10, py + 43, px + 40, py + 43);

        // Boat hull
        g.setColor(hullBrown);
        int[] hullX = { px + 8, px + 42, px + 36, px + 14 };
        int[] hullY = { py + 30, py + 30, py + 41, py + 41 };
        g.fillPolygon(hullX, hullY, 4);

        // Center mast
        g.setColor(mastBrown);
        g.fillRect(px + 24, py + 10, 3, 20);

        // Main right sail
        g.setColor(sailWhite);
        int[] mainSailX = { px + 27, px + 27, px + 41 };
        int[] mainSailY = { py + 11, py + 27, py + 27 };
        g.fillPolygon(mainSailX, mainSailY, 3);

        // Front left sail
        int[] jibSailX = { px + 23, px + 23, px + 12 };
        int[] jibSailY = { py + 14, py + 27, py + 27 };
        g.fillPolygon(jibSailX, jibSailY, 3);

        // Top flag
        g.setColor(flagRed);
        int[] flagX = { px + 24, px + 24, px + 17 };
        int[] flagY = { py + 10, py + 15, py + 12 };
        g.fillPolygon(flagX, flagY, 3);
    }
}