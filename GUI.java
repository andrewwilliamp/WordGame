import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class GUI implements ActionListener
{
    private JFrame frame;
    private JLabel playersLabel;
    private JButton addPlayerButton;
    private JLabel hostLabel;
    private String hostName;
    private JButton addHostButton;
    private JLabel playingPhraseLabel;
    private String phraseText;
    private JButton startTurnButton;
    private JLabel currentPlayerLabel;
    private JLabel currentPlayerMoneyLabel;
    private Players[] currentPlayers = new Players[3]; // Store player objects
    private Phrases phrases = new Phrases(); // Create an instance of Phrases
    private Hosts host; // Create an instance of Hosts
    int currentPlayerIndex = 0;            // Index to keep track of the current player
    private double currentPlayerMoney = 1000.0; // Store the current player's money
    Players player = new Players();
    public GUI()
    {
        // Create JFrame
        frame = new JFrame("Word Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLayout(null);

        // JLabel for listing current players
        playersLabel = new JLabel("Current Players:");
        frame.add(playersLabel);

        // JButton to add a player
        addPlayerButton = new JButton("Add Player");
        frame.add(addPlayerButton);

        // JLabel for host name
        hostLabel = new JLabel("Host Name:");
        frame.add(hostLabel);

        // JButton to add host name
        addHostButton = new JButton("Add Host Info");
        frame.add(addHostButton);

        // JLabel for playing phrase
        playingPhraseLabel = new JLabel("Playing Phrase:");
        frame.add(playingPhraseLabel);

        // JLabel for current player's name
        currentPlayerLabel = new JLabel("Current Player:");
        frame.add(currentPlayerLabel);

        // JLabel for current player's money
        currentPlayerMoneyLabel = new JLabel("Money: $");
        frame.add(currentPlayerMoneyLabel);
        currentPlayerMoneyLabel.setText("Money: $" + currentPlayerMoney);

        // JButton to start player's turn
        startTurnButton = new JButton("Start Turn");
        frame.add(startTurnButton);

        // Position components
        playersLabel.setBounds(20, 20, 150, 20);
        addPlayerButton.setBounds(250, 20, 150, 30);
        hostLabel.setBounds(20, 60, 150, 20);
        addHostButton.setBounds(250, 60, 150, 30);
        playingPhraseLabel.setBounds(20, 160, 150, 30);
        startTurnButton.setBounds(20, 200, 180, 30);
        currentPlayerLabel.setBounds(20, 100, 150, 20);
        currentPlayerMoneyLabel.setBounds(20, 120, 150, 20);

        // Add an ActionListener to the addPlayerButton
        addPlayerButton.addActionListener(this); // Register 'this' (the GUI instance) as the ActionListener
        addHostButton.addActionListener(this);
        startTurnButton.addActionListener(this);

        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    // Method to update the players label
    void updatePlayersLabel(int currentPlayerIndex)
    {
        // Build a string with player names
        StringBuilder playersText = new StringBuilder("Current Players:\n");
        for (int i = 0; i < 3; i++)
        {
            if (currentPlayers[i] != null) {
                playersText.append(" | ").append(currentPlayers[i].getFirstName());
            }
            playersText.append("\n");
        }
        // Set the updated text to the JLabel
        playersLabel.setText(playersText.toString());
    }

    // Method to update the host label
    void updateHostLabel(String hostName)
    {
        hostLabel.setText("Host Name: " + hostName);

    }

    // Method to update the current playing phrase
    void updatePhraseLabel(String playingPhrase)
    {
        playingPhraseLabel.setText("Playing Phrase: " + playingPhrase);
    }

    void updateCurrentPlayerLabel() {
        if (currentPlayers[currentPlayerIndex] != null) {
            currentPlayerLabel.setText("Current Player: " + currentPlayers[currentPlayerIndex].getFirstName());
        } else {
            currentPlayerLabel.setText("Current Player: ");
        }
    }

    String promptForPhrase() {
        return JOptionPane.showInputDialog(frame, "Enter the phrase for the Host:");
    }

    // Method to update the current player's money label
    void updateCurrentPlayerMoney(double money) {
        currentPlayerMoney = money;
        currentPlayerMoneyLabel.setText("Money: $" + currentPlayerMoney);
    }

    // Getter method for currentPlayerMoney
    public double getCurrentPlayerMoney() {
        return currentPlayerMoney;
    }

    // Method to handle starting the player's turn
    void startPlayerTurn() {
        if (currentPlayers[0] != null && host != null && !phrases.getPlayingPhrase().isEmpty()) {
            String guessedLetter = JOptionPane.showInputDialog(frame, "Enter a letter to guess:");
            Turn.takeTurn(currentPlayers[0], host, phrases, guessedLetter);
            updatePlayersLabel(currentPlayerIndex);
            boolean continuePlaying = true;
            while (continuePlaying) {
                updatePhraseLabel(phraseText);
            }
        }
    }

    // Method to handle restarting the game
    void restartGame() {
        for (int i = 0; i < 3; i++) {
            currentPlayers[i] = null;
        }
        playersLabel.setText("Current Players:");
        // Get a new phrase from the host
        String newPhrase = promptForPhrase();
        Phrases.setGamePhrase(newPhrase);
        updatePhraseLabel(Phrases.getPlayingPhrase()); // Update the "Playing Phrase" label
        host.resetGamePhrase();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == addPlayerButton) {
            String playerName = JOptionPane.showInputDialog(frame, "Enter the name of the new Player:");
            if (playerName != null && !playerName.isEmpty()) {
                // Find an empty slot for the player
                for (int i = 0; i < 3; i++) {
                    if (currentPlayers[i] == null) {
                        Players newPlayer = new Players();  // Create a new Players object
                        newPlayer.setFirstNoLast(playerName);
                        currentPlayers[i] = newPlayer;
                        currentPlayerIndex = i; // Set the current player index to the newly added player
                        break;
                    }
                }
                updatePlayersLabel(currentPlayerIndex);
            }
        } else if (source == addHostButton)     // Triggered when "Add Host" button is clicked
        {
            // If host name hasn't been entered yet, update host label
            if (hostLabel.getText().equals("Host Name:")) {
                hostName = JOptionPane.showInputDialog(frame, "Enter the name of the Host:");
                updateHostLabel(hostName);
                host = new Hosts(hostName);
            }
            // Get the phrase from the host
            String playingPhrase = promptForPhrase();
            Phrases.setGamePhrase(playingPhrase);
            updatePhraseLabel(Phrases.getPlayingPhrase());
        } else if (source == startTurnButton) {
            updateCurrentPlayerLabel();
            boolean gameWon = false;
            // **Need to add a check here to make sure all names and phrases are entered before guessing**

            String guessedLetter = JOptionPane.showInputDialog(frame, "Enter a letter to guess:");
            ArrayList<Object> turnResult = Turn.takeTurn(currentPlayers[currentPlayerIndex], host, phrases, guessedLetter);
            boolean guessedCorrectly = (boolean) turnResult.get(0);
            double amountWon = (double) turnResult.get(1);
            boolean continuePlaying = true;

            if (guessedCorrectly) {
                // Move to the next player in a circular manner
                currentPlayerIndex = (currentPlayerIndex + 1) % 3;
                updatePlayersLabel(currentPlayerIndex); // Update players label
                Random random = new Random();
                int randomPrizeType = random.nextInt(2);
                if (randomPrizeType == 0)
                {
                    // Money Prize
                    double CORRECT_GUESS_AMOUNT = 100.0; // Amount to increase for a correct guess
                    double INCORRECT_GUESS_AMOUNT = 50.0; // Amount to decrease for an incorrect guess
                    Money money = new Money(CORRECT_GUESS_AMOUNT, INCORRECT_GUESS_AMOUNT);
                    currentPlayerMoney += amountWon;
                    currentPlayerMoneyLabel.setText("Money: $" + currentPlayerMoney);
                    // display winnings and update user's money
                }
                else
                {
                    // Physical Prize

                }

                if (!phrases.getPlayingPhrase().contains("_")) {
                    gameWon = true;
                    if (gameWon) {
                        int option = JOptionPane.showConfirmDialog(frame, "You solved the puzzle and won the game!\nPlay another game? (Y/N)", "Game Over", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            restartGame();
                        } else {
                            System.exit(0);
                        }
                    }
                }
            }
        }
    }



    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run() {
                new GUI();
            }
        });
    }
}
