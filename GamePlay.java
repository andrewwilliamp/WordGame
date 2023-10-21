import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class GamePlay implements ActionListener {

    private GUI gui;
    private Players[] currentPlayers;
    private Players player;
    private Phrases phrases;
    private Hosts host;
    private String hostName;
    private JFrame frame;
    private JLabel hostLabel;
    private String playerName;

    public GamePlay(GUI gui) {
        this.gui = gui;
        this.currentPlayers = new Players[3];
        this.player = new Players();
        this.phrases = new Phrases();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        GUI gui= new GUI();
        Object source = e.getSource();
        if (source == gui.addPlayerMenuItem) {
            playerName = JOptionPane.showInputDialog(gui.getFrame(), "Enter the name of the new Player:");
            if (playerName != null && !playerName.isEmpty()) {
                // Find an empty slot for the player
                for (int i = 0; i < 3; i++) {
                    if (currentPlayers[i] == null) {
                        Players newPlayer = new Players();  // Create a new Players object
                        newPlayer.setFirstNoLast(playerName);
                        currentPlayers[i] = newPlayer;
                        gui.currentPlayerIndex = i; // Set the current player index to the newly added player
                        break;
                    }
                }
                gui.updatePlayersLabel(gui.currentPlayerIndex);
                gui.updateCurrentPlayerMoney(gui.currentPlayerIndex);

            }
        } else if (source == gui.addHostMenuItem)    // Triggered when "Add Host" button is clicked
        {
            // If host name hasn't been entered yet, update host label
            if (hostLabel.getText().equals("Host Name:")) {
                hostName = JOptionPane.showInputDialog(frame, "Enter the name of the Host:");
                gui.updateHostLabel(hostName);
                host = new Hosts(hostName);
            }
            // Get the phrase from the host
            String playingPhrase = gui.promptForPhrase();
            Phrases.setGamePhrase(playingPhrase);
            gui.updatePhraseLabel(Phrases.getPlayingPhrase());
        } else if (source == gui.layoutMenuItem) {
            JOptionPane.showMessageDialog(frame, "I decided to use the default JFrame layout manager for my game. \n" +
                    "I chose this option because I could easily set the bounds of each component, \n" +
                    "allowing for the most flexibility in my game's design.");
        }
        else if (source == gui.startTurnButton) {
            if (!gui.saveGameMessages.isSelected()) {
                gui.clearMessagesInTextArea();
            }

            if ( (playerName != null && !playerName.isEmpty()) && (!hostLabel.getText().equals("Host Name:")) ) {
                gui.updateCurrentPlayerLabel();
                boolean gameWon = false;
                String guessedLetter = JOptionPane.showInputDialog(frame, "Enter a letter to guess:");
                boolean turnResult = false;
                try {
                    turnResult = Turn.takeTurn(currentPlayers[gui.currentPlayerIndex], host, phrases, guessedLetter);
                } catch (MultipleLettersException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter one letter at a time");
                }
                boolean continuePlaying = true;

                if (turnResult) {
                    gui.updatePhraseLabel(Phrases.getPlayingPhrase());
                    Random random = new Random();
                    int randomPrizeType = random.nextInt(2);

                    if (randomPrizeType == 0) {
                        // Money Prize
                        double CORRECT_GUESS_AMOUNT = 100.0; // Amount to increase for a correct guess
                        double INCORRECT_GUESS_AMOUNT = 50.0; // Amount to decrease for an incorrect guess
                        Money money = new Money(CORRECT_GUESS_AMOUNT, INCORRECT_GUESS_AMOUNT);
                        // display winnings and update user's money
                        gui.currentPlayerMoney += CORRECT_GUESS_AMOUNT;
                        gui.currentPlayerMoneyLabel.setText("Money: $" + gui.currentPlayerMoney);
                        // switch to next player
                        gui.currentPlayerIndex = (gui.currentPlayerIndex + 1) % 3;
                        gui.updatePlayersLabel(gui.currentPlayerIndex); // Update players label
                        gui.updateCurrentPlayerLabel();
                        money.displayWinnings(frame, gui.gameMessagesTextArea, player, turnResult);
                    } else {
                        // Physical Prize
                        Physical physicalPrize = new Physical();
                        physicalPrize.displayWinnings(frame, gui.gameMessagesTextArea, player, turnResult);
                    }

                    if (!phrases.getPlayingPhrase().contains("_")) {
                        gameWon = true;
                        if (gameWon) {
                            int option = JOptionPane.showConfirmDialog(frame, "You solved the puzzle and won the game!\nPlay another game? (Y/N)", "Game Over", JOptionPane.YES_NO_OPTION);
                            if (option == JOptionPane.YES_OPTION) {
                                gui.restartGame();
                            } else {
                                System.exit(0);
                            }
                        }
                    }
                }
            }
            else {
                JOptionPane.showMessageDialog(frame, "Please enter host and player names first");
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI gui = new GUI();
                new GamePlay(gui);
            }
        });
    }

}

