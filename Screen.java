import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Screen extends JPanel {
    private static final int WIDTH = 650;
    private static final int HEIGHT = 500;
    private static final int TOP = 60;
    private static final int BUBBLE_COUNT = 24;

    private final BubbleList bubbles = new BubbleList(BUBBLE_COUNT);
    private final Thread timerThread;
    private final Thread animationThread;
    private int secondsLeft = 30;
    private boolean playing = true;
    private String result = "";

    public Screen() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);

        for (int i = 0; i < BUBBLE_COUNT; i++) {
            int x = 40 + (i % 6) * 100;
            int y = 85 + (i / 6) * 95;
            int dx = 1 + (int) (Math.random() * 3);
            int dy = -1 - (int) (Math.random() * 2);
            if (i % 2 == 0) {
                dx = -dx;
            }
            Bubble bubble = new Bubble(x, y, dx, dy, WIDTH, TOP, HEIGHT);
            bubbles.add(bubble);
            bubble.start();
        }

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                if (SwingUtilities.isLeftMouseButton(event)) {
                    popBubble(event.getX(), event.getY());
                }
            }
        });

        timerThread = new Thread(new Countdown(this));
        animationThread = new Thread(new Animate(this));
        timerThread.start();
        animationThread.start();
    }

    private void popBubble(int x, int y) {
        if (!playing) {
            return;
        }

        for (int i = bubbles.size() - 1; i >= 0; i--) {
            Bubble bubble = bubbles.get(i);
            if (bubble.contains(x, y)) {
                bubble.stop();
                bubbles.remove(i);
                if (bubbles.size() == 0) {
                    finish("You win!");
                }
                return;
            }
        }
    }

    // Countdown schedules this on the same Swing thread as clicks and drawing.
    public void tick() {
        if (playing) {
            secondsLeft--;
            if (secondsLeft == 0) {
                finish("You lose!");
            }
        }
    }

    private void finish(String message) {
        result = message;
        stopThreads();
        repaint();
    }

    public void stopThreads() {
        playing = false;
        timerThread.interrupt();
        animationThread.interrupt();
        for (int i = 0; i < bubbles.size(); i++) {
            bubbles.get(i).stop();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawString("Click the bubbles!", 20, 30);
        g.drawString("Bubbles left: " + bubbles.size(), 260, 30);
        g.drawString("Time: " + secondsLeft, 520, 30);
        g.drawLine(0, TOP - 1, WIDTH, TOP - 1);

        if (result.isEmpty()) {
            for (int i = 0; i < bubbles.size(); i++) {
                bubbles.get(i).drawMe(g);
            }
        } else {
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
            g.drawString(result, 270, 250);
        }
    }
}
