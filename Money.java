import javax.swing.*;

public class Money implements Award {
    private double winAmount;
    private double loseAmount;

    public Money(double winAmount, double loseAmount) {
        this.winAmount = winAmount;
        this.loseAmount = loseAmount;
    }


    @Override
    public void displayWinnings(JFrame frame, Players player, boolean correctGuess) {
        double CORRECT_GUESS_AMOUNT = 100.0; // Amount to increase for a correct guess
        double INCORRECT_GUESS_AMOUNT = 50.0;
        JOptionPane.showMessageDialog(frame, "Congrats, you won $" + CORRECT_GUESS_AMOUNT);
    }


    public void displayWinnings(JFrame frame, JTextArea textArea, Players player, boolean correctGuess) {
        double CORRECT_GUESS_AMOUNT = 100.0; // Amount to increase for a correct guess
        double INCORRECT_GUESS_AMOUNT = 50.0;
        textArea.append("Congrats, you won $" + CORRECT_GUESS_AMOUNT + "\n");
    }

}



