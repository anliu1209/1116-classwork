import java.awt.Color;
import java.awt.Graphics;

public class GridObject implements Runnable {
    private static final int SIZE = 46;
    private String name;

    public GridObject(String name) {
        this.name = name;
    }

    public synchronized void drawMe(Graphics g, int row, int column) {
        if name.equalsIgnoreCase("water"){
            g.drawString("")
            g.setColor(COLOR.blue);
            g.drawRect(column*50, row*50, 50, 50);
        }
        else if name.equalsIgnoreCase("dirt"){
            g.drawString("")
            g.setColor(COLOR.brown);
            g.drawRect(column*50, row*50, 50, 50);
        }
        else if name.equalsIgnoreCase("sand"){
            g.drawString("")
            g.setColor(COLOR.yellow);
            g.drawRect(column*50, row*50, 50, 50);
        }
        else if name.equalsIgnoreCase("grass"){
            g.drawString("")
            g.setColor(COLOR.green);
            g.drawRect(column*50, row*50, 50, 50);
        }
    }
}
