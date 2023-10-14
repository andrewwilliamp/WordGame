import javax.swing.SwingUtilities;

public class GamePlay {
static GUI gui = new GUI();
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            gui = new GUI();
        });
    }
}

