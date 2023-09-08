
import java.util.Scanner;
 public class Turn {
     private static final double CORRECT_GUESS_AMOUNT = 100.0; // Amount to increase for a correct guess
     private static final double INCORRECT_GUESS_AMOUNT = 50.0; // Amount to decrease for an incorrect guess
    Scanner scnr = new Scanner(System.in);

    public boolean takeTurn(Players players, Hosts host) {
        System.out.println(host.getFirstName() + " asks " + players.getFirstName() + " to guess a number between 0 and 100:");

        // Get the player's guess from user input
        int guess = scnr.nextInt();
        // Use the Numbers class to check the guess
        boolean isCorrect = Numbers.compareNumber(guess);

        if(isCorrect) {
            players.setMoney(players.getMoney() + CORRECT_GUESS_AMOUNT);
            System.out.println("Congratulations, " + players.getFirstName() + "! You guessed the number!");
            System.out.println(players.toString());
        }
        else {
            players.setMoney(players.getMoney() - INCORRECT_GUESS_AMOUNT);
            // Prints result and checks if guess was too high or low
            System.out.println("Sorry, your guess was " +
                    (guess > Numbers.getNum() ? "too high" : "too low") + ".");
            System.out.println(players.toString());
        }

        return isCorrect;

    }
}
