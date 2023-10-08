
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Turn {
    private static final double CORRECT_GUESS_AMOUNT = 100.0; // Amount to increase for a correct guess
    private static final double INCORRECT_GUESS_AMOUNT = 50.0; // Amount to decrease for an incorrect guess

    public static ArrayList<Object> takeTurn(Players players, Hosts host, Phrases phrases, String letter) {
        ArrayList<Object> result = new ArrayList<>();
        try {
            boolean guessedCorrectly = Phrases.findLetters(letter);
            double amountWon = 0.0; // Initialize to zero

            if (guessedCorrectly) {
                Random random = new Random();
                int randomPrizeType = random.nextInt(2);

                if (randomPrizeType == 0) {
                    // Money Prize
                    Money money = new Money(CORRECT_GUESS_AMOUNT, INCORRECT_GUESS_AMOUNT);
                    players.setMoney(players.getMoney() + amountWon);
                } else {
                    // Physical Prize
                    Physical physicalPrize = new Physical();
                    physicalPrize.getRandomPrize();
                    // You can handle physical prizes here if needed
                }
            }

            // Add boolean and double to the result ArrayList
            result.add(guessedCorrectly);
            result.add(amountWon);
        } catch (MultipleLettersException e) {
            System.out.println("Error: " + e.getMessage());
            // Add false and 0.0 to the result ArrayList
            result.add(false);
            result.add(0.0);
        }

        return result;
    }
}
