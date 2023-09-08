
import java.lang.System;
import java.util.Scanner;


public class GamePlay {

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        Players player = new Players();     // Create an instance of Players

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

        Hosts host = new Hosts();       // Create an instance of Hosts
        Turn turn = new Turn();         // Create an instance of Turn
        host.randomizeNum();            // Generate a new random number for the host

        boolean continuePlaying = true;
        while (continuePlaying) {
            host.randomizeNum();        // Generate a new random number for the host

            boolean guessedCorrectly = false;

            while(!guessedCorrectly) {
                guessedCorrectly = turn.takeTurn(player, host);
            }

            System.out.println("Do you want to continue playing? (Y/N)");

            String playAgainInput = scnr.next();
            if (!(playAgainInput.equalsIgnoreCase("Y"))) {
                continuePlaying = false;
            }
        }
        scnr.close();
    }
}

