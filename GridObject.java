import java.awt.Color;
import java.awt.Graphics;

public class GridObject {
    private static final int SIZE = 46;
    private String name;
    // water, dirt, sand, grass, tree, boulder
    private Color green = new Color(49, 92, 43);
    private Color blue = new Color(104, 176, 171);
    private Color beige = new Color(230, 170, 104);
    private Color brown = new Color(158, 98, 64);

    // Colors for tree and boulder details
    private Color trunkBrown = new Color(92, 56, 32);
    private Color leafDark = new Color(30, 68, 26);
    private Color leafLight = new Color(82, 145, 66);
    private Color rockGray = new Color(128, 132, 136);
    private Color rockHighlight = new Color(172, 176, 180);

    public GridObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public synchronized void drawMe(Graphics g, int row, int column) {
        int px = column * 50;
        int py = row * 50;

        if (name.equalsIgnoreCase("water")){
            g.setColor(blue);
            g.fillRect(px, py, 50, 50);
        }
        else if (name.equalsIgnoreCase("dirt")){
            g.setColor(brown);
            g.fillRect(px, py, 50, 50);
        }
        else if (name.equalsIgnoreCase("sand")){
            g.setColor(beige);
            g.fillRect(px, py, 50, 50);
        }
        else if (name.equalsIgnoreCase("grass")){
            g.setColor(green);
            g.fillRect(px, py, 50, 50);
        }
        else if (name.equalsIgnoreCase("tree")){
            // Trunk
            g.setColor(trunkBrown);
            g.fillRect(px + 21, py + 31, 8, 13);

            // Lower tier
            g.setColor(leafDark);
            int[] lowerX = { px + 10, px + 40, px + 25 };
            int[] lowerY = { py + 33, py + 33, py + 15 };
            g.fillPolygon(lowerX, lowerY, 3);

            // Upper tier
            g.setColor(leafLight);
            int[] upperX = { px + 13, px + 37, px + 25 };
            int[] upperY = { py + 22, py + 22, py + 6 };
            g.fillPolygon(upperX, upperY, 3);
        }
        else if (name.equalsIgnoreCase("boulder")){
            // Main rock
            g.setColor(rockGray);
            int[] rockX = { px + 10, px + 16, px + 32, px + 41, px + 37, px + 13 };
            int[] rockY = { py + 35, py + 18, py + 16, py + 28, py + 39, py + 39 };
            g.fillPolygon(rockX, rockY, 6);

            // Top-left highlight
            g.setColor(rockHighlight);
            int[] highlightX = { px + 16, px + 30, px + 26, px + 15 };
            int[] highlightY = { py + 19, py + 18, py + 26, py + 27 };
            g.fillPolygon(highlightX, highlightY, 4);
        }
    }
}