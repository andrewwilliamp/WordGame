import javax.swing.*;
import java.util.Random;

public class Physical implements Award {
    private String[] physicalPrizes;
    public Physical() {
        physicalPrizes = new String[]{
                "Television",
                "Vacation Package",
                "Pellet Grill",
                "Car",
                "Washing Machine"
        };
    }

    public int getRandomPrize() {
        Random random = new Random();
        int randomIndex = random.nextInt(physicalPrizes.length);
        return randomIndex;
    }


    @Override
    public void displayWinnings(JFrame frame, Players player, boolean correctGuess) {
        if(correctGuess) {
            int prizeIndex = getRandomPrize();
            JOptionPane.showMessageDialog(frame, "Congrats, you won a " + physicalPrizes[prizeIndex]);
        }
        else {
            int prizeIndex = getRandomPrize();
            System.out.println(player.getFirstName() + " missed out on the chance to win a " + physicalPrizes[prizeIndex]);
        }

    }

    // Overloaded method for appending to text area instead of displaying JOptionPane
    public void displayWinnings(JFrame frame, JTextArea textArea, Players player, boolean correctGuess) {
        if(correctGuess) {
            int prizeIndex = getRandomPrize();
            textArea.append("Congrats, you won a " + physicalPrizes[prizeIndex] + "\n");
        }
        else {
            int prizeIndex = getRandomPrize();
            System.out.println(player.getFirstName() + " missed out on the chance to win a " + physicalPrizes[prizeIndex]);
        }

    }
}
