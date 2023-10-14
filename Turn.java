
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Turn {
    private static final double CORRECT_GUESS_AMOUNT = 100.0; // Amount to increase for a correct guess
    private static final double INCORRECT_GUESS_AMOUNT = 50.0; // Amount to decrease for an incorrect guess

    public static boolean takeTurn(Players players, Hosts host, Phrases phrases, String letter) throws MultipleLettersException {
        boolean guessResult = Phrases.findLetters(letter);
        return guessResult;
    }
}
