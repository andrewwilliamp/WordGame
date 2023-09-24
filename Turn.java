import java.util.Scanner;
import java.util.Random;

public class Turn {
    private static final double CORRECT_GUESS_AMOUNT = 100.0; // Amount to increase for a correct guess
    private static final double INCORRECT_GUESS_AMOUNT = 50.0; // Amount to decrease for an incorrect guess
    Scanner scnr = new Scanner(System.in);

    public boolean takeTurn(Players players, Hosts host, Phrases phrases) {
        // Get the player's guess as a letter from user input
        String letter = scnr.nextLine().trim();

        try {
            boolean guessedCorrectly = Phrases.findLetters(letter);
            if (guessedCorrectly) {
                Random random = new Random();
                int randomPrizeType = random.nextInt(2);

                if (randomPrizeType == 0) {
                    // Money Prize
                    Money money = new Money(CORRECT_GUESS_AMOUNT, INCORRECT_GUESS_AMOUNT);
                    money.displayWinnings(players, true);
                    players.setMoney(players.getMoney() + CORRECT_GUESS_AMOUNT);
                } else {
                    // Physical Prize
                    Physical physicalPrize = new Physical();
                    physicalPrize.getRandomPrize();
                    physicalPrize.displayWinnings(players, true);
                }
            }
            return guessedCorrectly;
        } catch (MultipleLettersException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}
