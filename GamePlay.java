
import java.lang.System;
import java.util.Scanner;


public class GamePlay {
    private static Players[] currentPlayers = new Players[3];   // Declare and initialize the array
    private static Phrases phrases = new Phrases();             // Create an instance of Phrases

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

        System.out.println("Please enter the host's name: ");
        String hostName = scnr.next();

        Hosts host = new Hosts(hostName);       // Create an instance of Hosts
        host.enterPhrase();

        Turn turn = new Turn();                 // Create an instance of Turn

        int currentPlayerIndex = 0;             // Index to keep track of the current player
        boolean continuePlaying = true;
        boolean gameWon = false;

        while (continuePlaying) {
            System.out.println(host.getFirstName() + " says, \"the phrase to guess is: " +
                    phrases.getPlayingPhrase() + "\"");
            Players currentPlayer = currentPlayers[currentPlayerIndex];
            System.out.println("It is now the following player's turn:");
            System.out.println(currentPlayers[currentPlayerIndex]);

            boolean guessedCorrectly = turn.takeTurn(currentPlayer, host, phrases); // Update guessedCorrectly
            if (guessedCorrectly) {
                currentPlayerIndex = (currentPlayerIndex + 1) % 3;  // Move to the next player in a circular manner
            }

            // Check if the phrase is solved after each guess
            if (!phrases.getPlayingPhrase().contains("_")) {
                gameWon = true;
            }

            if (gameWon) {
                System.out.println("You solved the puzzle and won the game!");
                System.out.println("Play another game? (Y/N)");
                String playAgainInput = scnr.next();
                if (!(playAgainInput.equalsIgnoreCase("Y"))) {
                    continuePlaying = false;
                } else {
                    host.resetGamePhrase();             // Clear game phrase so host can enter another
                    host.enterPhrase();
                    gameWon = false; // Reset the gameWon flag for the new game
                }
            }
        }
        scnr.close();
    }
}
