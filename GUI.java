
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.UIManager;
import java.util.ArrayList;
import java.util.Random;

public class GUI implements ActionListener {
    private JFrame frame;
    private JLabel playersLabel;
    private JButton addPlayerButton;
    private JLabel hostLabel;
    private String hostName;
    private JButton addHostButton;
    private JLabel playingPhraseLabel;
    private String phraseText;
    public JButton startTurnButton;
    public JLabel currentPlayerLabel;
    public JLabel currentPlayerMoneyLabel;
    JMenuItem addPlayerMenuItem;
    JMenuItem addHostMenuItem;
    JMenuItem layoutMenuItem;
    public JTextArea gameMessagesTextArea;
    public JCheckBox saveGameMessages;
    private Players[] currentPlayers = new Players[3]; // Store player objects
    private Phrases phrases = new Phrases(); // Create an instance of Phrases
    private Hosts host; // Create an instance of Hosts
    int currentPlayerIndex = 0;            // Index to keep track of the current player
    public double currentPlayerMoney = 1000.0; // Store the current player's money
    Players player = new Players();

    public GUI() {

        // Create JFrame
        frame = new JFrame("Word Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLayout(null);

        // Menu called Game, accessed by Alt + G
        JMenuBar mb = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G);
        mb.add(gameMenu);


        // Add Game Menu Items
        addPlayerMenuItem = new JMenuItem("Add Player");
        addHostMenuItem = new JMenuItem("Add Host");
        gameMenu.add(addPlayerMenuItem);
        gameMenu.add(addHostMenuItem);
        addPlayerMenuItem.addActionListener(this);
        addHostMenuItem.addActionListener(this);

        // Menu called About, accessed by Alt + A
        JMenu aboutMenu = new JMenu("About");
        aboutMenu.setMnemonic(KeyEvent.VK_A);
        mb.add(aboutMenu);

        // Add About Menu Items
        layoutMenuItem = new JMenuItem("Layout");
        aboutMenu.add(layoutMenuItem);
        layoutMenuItem.addActionListener(this);

        // JLabel for listing current players
        playersLabel = new JLabel("Players:");
        frame.add(playersLabel);

        // JLabel for host name
        hostLabel = new JLabel("Host Name:");
        frame.add(hostLabel);

        // JLabel for playing phrase
        playingPhraseLabel = new JLabel("Playing Phrase:");
        frame.add(playingPhraseLabel);

        // JLabel for current player's name
        currentPlayerLabel = new JLabel("Current Player:");
        frame.add(currentPlayerLabel);

        // JLabel for current player's money
        currentPlayerMoneyLabel = new JLabel("Money: $");
        frame.add(currentPlayerMoneyLabel);
        currentPlayerMoneyLabel.setText("Money: $0.00");

        // JButton to start player's turn
        startTurnButton = new JButton("Start Turn");
        startTurnButton.addActionListener(this);
        frame.add(startTurnButton);

        // JTextArea to hold all game messages
        gameMessagesTextArea = new JTextArea();
        gameMessagesTextArea.setEditable(false);
        gameMessagesTextArea.setLineWrap(true); // Enable line wrapping
        gameMessagesTextArea.setWrapStyleWord(true); // Wrap text at word boundaries

        // JScrollPane to hold JTextArea
        JScrollPane gameMessagesScrollPane = new JScrollPane(gameMessagesTextArea);
        gameMessagesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(gameMessagesScrollPane);


        // JCheckbox to check if users want to save previous game messages
        saveGameMessages = new JCheckBox("Save Game Messages");
        saveGameMessages.createToolTip();
        saveGameMessages.setToolTipText("Check this box to save all previous messages");
        frame.add(saveGameMessages);

        // Position components
        playersLabel.setBounds(20, 20, 300, 20);
        hostLabel.setBounds(20, 40, 200, 20);
        startTurnButton.setBounds(20, 200, 180, 30);
        currentPlayerLabel.setBounds(20, 80, 150, 20);
        currentPlayerMoneyLabel.setBounds(20, 100, 150, 20);
        playingPhraseLabel.setBounds(20, 140, 300, 30);
        gameMessagesScrollPane.setBounds(300, 20, 450, 280);
        saveGameMessages.setBounds(300, 310, 200, 20);

        frame.setVisible(true);

        frame.setJMenuBar(mb);
        frame.validate();
        frame.repaint();
    }

    public JFrame getFrame() {
        return frame;
    }

    // Method to update the players label
    void updatePlayersLabel(int currentPlayerIndex) {
        // Build a string with player names
        StringBuilder playersText = new StringBuilder("Players:\n");
        for (int i = 0; i < 3; i++) {
            if (currentPlayers[i] != null) {
                playersText.append(" | ").append(currentPlayers[i].getFirstName());
            }
            playersText.append("\n");
        }
        // Set the updated text to the JLabel
        playersLabel.setText(playersText.toString());
    }

    void resetPlayersLabel() {
        playersLabel.setText("Players: ");
    }

    // Method to update the host label
    void updateHostLabel(String hostName) {
        hostLabel.setText("Host Name: " + hostName);

    }

    // Method to update the current playing phrase
    void updatePhraseLabel(String playingPhrase) {
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

    void resetMoney() {
        currentPlayerMoneyLabel.setText("Money: $0.00");
    }

    public void appendMessageToTextArea(String messageText) {
        gameMessagesTextArea.append(messageText + "\n");
    }

    public void clearMessagesInTextArea() {
        gameMessagesTextArea.setText("");
    }

    // Method to handle starting the player's turn
    void startPlayerTurn() throws MultipleLettersException {
        if (currentPlayers[0] != null && host != null && !phrases.getPlayingPhrase().isEmpty()) {
            String guessedLetter = JOptionPane.showInputDialog(frame, "Enter a letter to guess:");
            Turn.takeTurn(currentPlayers[0], host, phrases, guessedLetter);
            updatePlayersLabel(currentPlayerIndex);
            boolean continuePlaying = true;
            while (continuePlaying) {
                phraseText = Phrases.getPlayingPhrase();
                updatePhraseLabel(phraseText);
            }
        }
    }

    // Method to handle restarting the game
    void restartGame() {
        for (int i = 0; i < 3; i++) {
            currentPlayers[i] = null;
            currentPlayerMoney = 1000.0;
        }
        playersLabel.setText("Players:");
        // Get a new phrase from the host
        String phrase = "";
        Phrases.setGamePhrase(phrase);
        updatePhraseLabel(Phrases.getPlayingPhrase());      // Update the "Playing Phrase" label
        resetPlayersLabel();
        currentPlayerLabel.setText("Current Player: ");
        resetMoney();
        clearMessagesInTextArea();
    }

    String playerName;

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == addPlayerMenuItem) {
            playerName = JOptionPane.showInputDialog(frame, "Enter the name of the new Player:");
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
                updateCurrentPlayerMoney(currentPlayerMoney);

            }
        } else if (source == addHostMenuItem)     // Triggered when "Add Host" button is clicked
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
        } else if (source == layoutMenuItem) {
            JOptionPane.showMessageDialog(frame, "I decided to use the default JFrame layout manager for my game. \n" +
                    "I chose this option because I could easily set the bounds of each component, \n" +
                    "allowing for the most flexibility in my game's design.");
        } else if (source == startTurnButton) {
            if (!saveGameMessages.isSelected()) {
                clearMessagesInTextArea();
            }

            if ((playerName != null && !playerName.isEmpty()) && (!hostLabel.getText().equals("Host Name:"))) {
                updateCurrentPlayerLabel();
                boolean gameWon = false;
                String guessedLetter = JOptionPane.showInputDialog(frame, "Enter a letter to guess:");
                boolean turnResult = false;
                try {
                    turnResult = Turn.takeTurn(currentPlayers[currentPlayerIndex], host, phrases, guessedLetter);
                } catch (MultipleLettersException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter one letter at a time");
                }
                boolean continuePlaying = true;

                if (turnResult) {
                    updatePhraseLabel(Phrases.getPlayingPhrase());
                    Random random = new Random();
                    int randomPrizeType = random.nextInt(2);

                    if (randomPrizeType == 0) {
                        // Money Prize
                        double CORRECT_GUESS_AMOUNT = 100.0; // Amount to increase for a correct guess
                        double INCORRECT_GUESS_AMOUNT = 50.0; // Amount to decrease for an incorrect guess
                        Money money = new Money(CORRECT_GUESS_AMOUNT, INCORRECT_GUESS_AMOUNT);
                        // display winnings and update user's money
                        currentPlayerMoney += CORRECT_GUESS_AMOUNT;
                        currentPlayerMoneyLabel.setText("Money: $" + currentPlayerMoney);
                        // switch to next player
                        currentPlayerIndex = (currentPlayerIndex + 1) % 3;
                        updatePlayersLabel(currentPlayerIndex); // Update players label
                        updateCurrentPlayerLabel();
                        money.displayWinnings(frame, gameMessagesTextArea, player, turnResult);
                    } else {
                        // Physical Prize
                        Physical physicalPrize = new Physical();
                        physicalPrize.displayWinnings(frame, gameMessagesTextArea, player, turnResult);
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
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter host and player names first");
            }
        }
    }
}

