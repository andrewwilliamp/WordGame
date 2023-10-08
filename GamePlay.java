import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePlay {
    private static Players[] currentPlayers = new Players[3];
    private static Phrases phrases = new Phrases();
    private static Hosts host;
    private static GUI gui;
    private static int currentPlayerIndex = 0;
    private static boolean firstPlayerEntered = false; // Flag to track if the first player entered their name

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            gui = new GUI();
        });
    }

    public static void addPlayer(String playerName) {
        for (int i = 0; i < 3; i++) {
            if (currentPlayers[i] == null) {
                Players newPlayer = new Players();
                newPlayer.setFirstNoLast(playerName);
                currentPlayers[i] = newPlayer;
                currentPlayerIndex = i; // Update the current player index
                gui.updatePlayersLabel(i);
                firstPlayerEntered = true;
                gui.updateCurrentPlayerLabel(); // Update the current player label after the first player enters their name
                break;
            }
        }
    }

    public static void addHost(String hostName) {
        host = new Hosts(hostName);
        gui.updateHostLabel(hostName);

        String playingPhrase = gui.promptForPhrase();
        Phrases.setGamePhrase(playingPhrase);
        gui.updatePhraseLabel(Phrases.getPlayingPhrase());
    }

    public static void startPlayerTurn(String guessedLetter) {
        ArrayList<Object> turnResult = Turn.takeTurn(currentPlayers[currentPlayerIndex], host, phrases, guessedLetter);
        boolean guessedCorrectly = (boolean) turnResult.get(0);
        double amountWon = (double) turnResult.get(1);
        if (guessedCorrectly) {
            Random random = new Random();
            int randomPrizeType = random.nextInt(2);
            if (randomPrizeType == 0) {
                double CORRECT_GUESS_AMOUNT = 100.0;
                double INCORRECT_GUESS_AMOUNT = 50.0;
                Money money = new Money(CORRECT_GUESS_AMOUNT, INCORRECT_GUESS_AMOUNT);
                double currentPlayerMoney = gui.getCurrentPlayerMoney();
                currentPlayerMoney += amountWon;
            } else {
                // Handle physical prize
            }

            if (!phrases.getPlayingPhrase().contains("_")) {
                boolean gameWon = true;
                if (gameWon) {
                    int option = JOptionPane.showConfirmDialog(gui.getFrame(), "You solved the puzzle and won the game!\nPlay another game? (Y/N)", "Game Over", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        gui.restartGame();
                    } else {
                        System.exit(0);
                    }
                }
            }
        }

        // Move to the next player in a circular manner
        currentPlayerIndex = (currentPlayerIndex + 1) % 3;
        gui.updatePlayersLabel(currentPlayerIndex); // Update players label
        gui.updateCurrentPlayerLabel(); // Update current player label after a player takes their turn
    }
}
