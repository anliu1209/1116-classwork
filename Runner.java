import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Runner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("HashMap Grid");
            Screen screen = new Screen();
            frame.add(screen);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            // Stop movement and animation threads on close
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent event) {
                    screen.stopThreads();
                }
            });
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}