
import java.lang.System;
import java.util.Scanner;


public class GamePlay {
    private static Person player;

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        player = new Person();

        System.out.println("Enter your name to play the game.");
        System.out.println("Would you like to enter your last name? (Y/N)");
        String input = scnr.next();


        if ( (input.equals("Y")) || (input.equals("y")) ) {
            System.out.println("Enter your first name.");
            String firstName = scnr.next();
            System.out.println("Enter your last name.");
            String lastName = scnr.next();
            player.setFirstAndLast(firstName, lastName);
        }
        else if ( (input.equals("N")) || (input.equals("n")) ) {
            System.out.println("Enter your first name.");
            String firstName = scnr.next();
            player.setFirstNoLast(firstName);
        }
        else {
            System.out.println("Please enter the value Y, y, N, or n.");
        }

        Numbers numberGame = new Numbers();
        numberGame.generateNumber();
        int playerGuess;
        do {
            System.out.print(player.getFirstName() + ", enter your guess (0 - 100): ");
            playerGuess = scnr.nextInt();
        } while (!numberGame.compareNumber(playerGuess));


        scnr.close();


    }
}
