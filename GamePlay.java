
import java.lang.System;
import java.util.Scanner;


public class GamePlay {
    private static Players[] currentPlayers = new Players[3]; // Declare and initialize the array

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        // Loop to create and add 3 players to the currentPlayers array
        for (int i = 0; i < 3; i++) {
            System.out.println("Welcome to the Game, Player " + (i + 1));
            System.out.println("We need at least your first name, " +
                    "would you like to enter your last name? (Y/N)");
            String input = scnr.next();
            Players player = new Players();

            if ((input.equals("Y")) || (input.equals("y"))) {
                System.out.println("Player " + (i + 1) + ", enter your first name.");
                String firstName = scnr.next();
                System.out.println("Enter your last name.");
                String lastName = scnr.next();
                player.setFirstAndLast(firstName, lastName);
            } else if ((input.equals("N")) || (input.equals("n"))) {
                System.out.println("Player " + (i + 1) + ", enter your first name.");
                String firstName = scnr.next();
                player.setFirstNoLast(firstName);
            } else {
                System.out.println("Please enter the value Y, y, N, or n.");
                i--; // Decrement i to re-enter player information for this player
                continue;
            }
            currentPlayers[i] = player;
        }

        Hosts host = new Hosts();       // Create an instance of Hosts
        Turn turn = new Turn();         // Create an instance of Turn
        host.randomizeNum();            // Generate a new random number for the host

        boolean continuePlaying = true;
        int currentPlayerIndex = 0;     // Index to keep track of the current player

        while (continuePlaying) {
            host.randomizeNum();        // Generate a new random number for the host

            boolean guessedCorrectly = false;

            while(!guessedCorrectly) {
                Players currentPlayer = currentPlayers[currentPlayerIndex];
                guessedCorrectly = turn.takeTurn(currentPlayer, host);
                currentPlayerIndex = (currentPlayerIndex + 1) % 3;  // Move to the next player in a circular manner
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
