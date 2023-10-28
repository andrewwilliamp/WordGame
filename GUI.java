import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.sound.sampled.*;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class GUI implements ActionListener {
    private JFrame frame;
    private JLabel playersLabel;
    private JLabel hostLabel;
    private String hostName;
    private JLabel playingPhraseLabel;
    private String phraseText;
    public JButton startTurnButton;
    public JLabel currentPlayerLabel;
    public JLabel currentPlayerMoneyLabel;
    JMenuItem addPlayerMenuItem;
    JMenuItem addHostMenuItem;
    JMenuItem layoutMenuItem;
    JMenuItem attributionMenuItem;
    public JTextArea gameMessagesTextArea;
    public JLabel displayImage;
    public JLabel displayAnimatedImage;
    public JLabel displayArrowImage;
    public ImageIcon image;
    public ImageIcon rotatedFwdImage;
    public JCheckBox saveGameMessages;
    private Players[] currentPlayers = new Players[3]; // Store player objects
    private Phrases phrases = new Phrases(); // Create an instance of Phrases
    private Hosts host; // Create an instance of Hosts
    int currentPlayerIndex = 0;            // Index to keep track of the current player
    public double currentPlayerMoney = 1000.0; // Store the current player's money
    Players player = new Players();
    public Clip audioClip;
    public AudioInputStream audio;
    public GUI() throws IOException {

        // Create JFrame
        frame = new JFrame("Word Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 470);
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

        // Add About Menu Item
        layoutMenuItem = new JMenuItem("Layout");
        aboutMenu.add(layoutMenuItem);
        layoutMenuItem.addActionListener(this);


        // Add Attribution Menu Item
        attributionMenuItem = new JMenuItem("Attribution");
        aboutMenu.add(attributionMenuItem);
        attributionMenuItem.addActionListener(this);

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

        // Create the displayImage JLabel
        displayImage = new JLabel();
        frame.add(displayImage);

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

        // Rotating Image
        ImageIcon originalImage = new ImageIcon("mygamewheel.png");
        BufferedImage rotatedFwdImage = rotate(originalImage, 90.0d);
        BufferedImage rotatedBackwardImage = rotate(originalImage, -90.0d);
        JLabel originalImageLabel = new JLabel(originalImage);
        frame.add(originalImageLabel);
        JLabel rotatedFwdImageLabel = new JLabel(new ImageIcon(rotatedFwdImage));
        frame.add(rotatedFwdImageLabel);
        JLabel rotatedBackwardImageLabel = new JLabel(new ImageIcon(rotatedBackwardImage));
        frame.add(rotatedBackwardImageLabel);


        // Position components
        playersLabel.setBounds(20, 20, 300, 20);
        hostLabel.setBounds(20, 40, 200, 20);
        currentPlayerLabel.setBounds(20, 80, 150, 20);
        currentPlayerMoneyLabel.setBounds(20, 100, 150, 20);
        playingPhraseLabel.setBounds(20, 140, 300, 30);
        startTurnButton.setBounds(20, 200, 180, 30);
        gameMessagesScrollPane.setBounds(300, 20, 450, 190);
        saveGameMessages.setBounds(300, 210, 200, 20);

        frame.getContentPane().setBackground(Color.decode("#FFFFFF") ); // set background color
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

    // Methods to set/play audio file
    public void setAudioFile(String soundFileName) {
        try {
            File file = new File(soundFileName);
            audio = AudioSystem.getAudioInputStream(file);
            audioClip = AudioSystem.getClip();
            audioClip.open(audio);
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public void playAudio() throws InterruptedException, IOException {
        if (audioClip != null) {
            audioClip.start();
        }
    }

    public void stopAudio() throws IOException {
        if (audioClip != null) {
            audioClip.stop();
            audioClip.close();
        }
    }

    public BufferedImage rotate(ImageIcon image, double degrees) {
        // Calculate the new size of the image based on the angle of rotation
        double radians = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int newWidth = (int) Math.max(1, Math.round(image.getIconWidth() * cos + image.getIconHeight() * sin));
        int newHeight = (int) Math.max(1, Math.round(image.getIconWidth() * sin + image.getIconHeight() * cos));
        // Create a new image
        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();
        // Calculate the "anchor" point around which the image will be rotated
        int x = (newWidth - image.getIconWidth()) / 2;
        int y = (newHeight - image.getIconHeight()) / 2;
        // Transform the origin point around the anchor point
        AffineTransform at = new AffineTransform();
        at.setToRotation(radians, x + (image.getIconWidth() / 2), y + (image.getIconHeight() / 2));
        at.translate(x, y);

        g2d.setTransform(at);
        // Preserve quality when rotating
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Paint the original image
        g2d.drawImage(image.getImage(), 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }



    void resetMoney() {
        currentPlayerMoneyLabel.setText("Money: $0.00");
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
        if (displayImage != null || displayAnimatedImage != null || displayArrowImage != null) {
            // Remove the all image JLabels and set their icons to null
            frame.remove(displayImage);
            frame.remove(displayAnimatedImage);
            frame.remove(displayArrowImage);
            displayImage.setIcon(null);
            displayAnimatedImage.setIcon(null);
            displayArrowImage.setIcon(null);
            frame.repaint();
        }
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
        } else if(source == attributionMenuItem) {
            JOptionPane.showMessageDialog(frame, "Icons:\n" + "money_img.png was generated by DALLE AI through Canva\n" +
                    "prize_img.png was generated by DALL-E AI through Canva\n" + "Sounds:\n" +
                    "money_mp3.wav was found on https://pixabay.com/sound-effects/search/ching/\n" +
                    "prize.wav was found on https://mixkit.co/free-sound-effects/win/\n" +
                    "wheel.gif was found on https://giphy.com/gifs/BigTimeGaming-official-btg-wof-big-time-gaming-8wVRtdu0M1u0AvcDVM");
        } else if (source == startTurnButton) {
            if (!saveGameMessages.isSelected()) {
                clearMessagesInTextArea();
            }
            if (displayImage != null) {
                // Remove the displayImage JLabel and set its icon to null
                frame.remove(displayImage);
                displayImage.setIcon(null);
                frame.repaint();
            }
            if (displayAnimatedImage != null) {
                // Remove the displayImage JLabel and set its icon to null
                frame.remove(displayAnimatedImage);
                displayAnimatedImage.setIcon(null);
                frame.repaint();
            }
            // Image of arrow for game wheel
            ImageIcon arrowImage = new ImageIcon("game_arrow.png");
            if (arrowImage.getImageLoadStatus() == MediaTracker.ERRORED) {
                System.out.println("Image loading error.");
            }
            displayArrowImage = new JLabel();
            displayArrowImage.setIcon(arrowImage);
            displayArrowImage.setBounds(365, 220, 50, 50);
            frame.add(displayArrowImage);
            // Game wheel image
            ImageIcon image = new ImageIcon("mygamewheel.png");
            if (image.getImageLoadStatus() == MediaTracker.ERRORED) {
                System.out.println("Image loading error.");
            }
            displayAnimatedImage = new JLabel();
            displayImage = new JLabel();
            displayImage.setIcon(image);
            displayImage.setBounds(300, 255, 180, 150);
            frame.add(displayImage);

            frame.repaint();

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
                    int randomPrizeType = random.nextInt(5);

                    if (displayImage != null) {
                        // Remove the displayImage JLabel and set its icon to null
                        frame.remove(displayImage);
                        displayImage.setIcon(null);
                        frame.repaint();
                    }

                    try {
                        if (randomPrizeType == 0) {
                            // Money Prize
                            // Remove image JLabels and set their icons to null
                            frame.remove(displayImage);
                            frame.remove(displayAnimatedImage);
                            displayImage.setIcon(null);
                            displayAnimatedImage.setIcon(null);
                            frame.repaint();
                            // Play money audio
                            String soundTrack = "money_mp3.wav";
                            setAudioFile(soundTrack);
                            playAudio();
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
                            // Rotate wheel image to land on money
                            BufferedImage rotatedFwdImage = rotate(image, 90.0d);
                            ImageIcon rotatedFwdImage_ = new ImageIcon(rotatedFwdImage);
                            displayAnimatedImage.setIcon(rotatedFwdImage_);
                            displayAnimatedImage.setBounds(300, 255, 180, 150);
                            frame.add(displayAnimatedImage);
                            // Set Image to money image
                            image = new ImageIcon("money_img.png");
                            displayImage.setIcon(image);
                            displayImage.setBounds(20, 235, 180, 150);
                            frame.add(displayImage);
                            frame.repaint();

                        } else {
                            // Physical Prize
                            // Remove image JLabels and set their icons to null
                            frame.remove(displayImage);
                            frame.remove(displayAnimatedImage);
                            displayImage.setIcon(null);
                            displayAnimatedImage.setIcon(null);
                            frame.repaint();
                            // Play prize audio
                            String soundTrack = "prize.wav";
                            setAudioFile(soundTrack);
                            playAudio();

                            Physical physicalPrize = new Physical();
                            int prizeIndex = physicalPrize.displayWinnings(frame, gameMessagesTextArea, player, turnResult);
                            // Set Image to prize image
                            image = new ImageIcon("prize_img.png");

                            displayImage.setIcon(image);
                            displayImage.setBounds(20, 235, 180, 150);
                            frame.add(displayImage);
                            System.out.println("Prize index:" + prizeIndex);
                            rotatedFwdImage = new ImageIcon("mygamewheel.png");
                            if (prizeIndex == 0) {
                                // Remove image JLabels and set their icons to null

                                frame.remove(displayAnimatedImage);

                                displayAnimatedImage.setIcon(null);
                                frame.repaint();
                                // Rotate wheel image to land on TV prize
                                BufferedImage rotatedFwdImage_ = rotate(rotatedFwdImage, -90.0d);
                                ImageIcon rotatedFwdImage = new ImageIcon(rotatedFwdImage_);
                                displayAnimatedImage.setIcon(rotatedFwdImage);
                                displayAnimatedImage.setBounds(300, 255, 180, 150);
                                frame.add(displayAnimatedImage);
                            }
                            else if (prizeIndex == 1) {
                                // Remove image JLabels and set their icons to null

                                frame.remove(displayAnimatedImage);

                                displayAnimatedImage.setIcon(null);
                                frame.repaint();
                                // Rotate wheel image to land on Vacation Package prize
                                BufferedImage rotatedFwdImage_ = rotate(rotatedFwdImage, -25.0d);
                                ImageIcon rotatedFwdImage = new ImageIcon(rotatedFwdImage_);
                                displayAnimatedImage.setIcon(rotatedFwdImage);
                                displayAnimatedImage.setBounds(300, 255, 180, 150);
                                frame.add(displayAnimatedImage);
                            }
                            else if (prizeIndex == 2) {
                                // Remove image JLabels and set their icons to null
                                frame.remove(displayAnimatedImage);

                                displayAnimatedImage.setIcon(null);
                                frame.repaint();
                                // Rotate wheel image to land on Pellet Grill prize
                                BufferedImage rotatedFwdImage_ = rotate(rotatedFwdImage, 22.5d);
                                ImageIcon rotatedFwdImage = new ImageIcon(rotatedFwdImage_);
                                displayAnimatedImage.setIcon(rotatedFwdImage);
                                displayAnimatedImage.setBounds(300, 255, 180, 150);
                                frame.add(displayAnimatedImage);
                            }
                            else if (prizeIndex == 3) {
                                // Remove image JLabels and set their icons to null

                                frame.remove(displayAnimatedImage);

                                displayAnimatedImage.setIcon(null);
                                frame.repaint();
                                // Rotate wheel image to land on Car prize
                                BufferedImage rotatedFwdImage_ = rotate(rotatedFwdImage, 120.5d);
                                ImageIcon rotatedFwdImage = new ImageIcon(rotatedFwdImage_);
                                displayAnimatedImage.setIcon(rotatedFwdImage);
                                displayAnimatedImage.setBounds(300, 255, 180, 150);
                                frame.add(displayAnimatedImage);
                            }
                            else if (prizeIndex == 4) {
                                // Remove image JLabels and set their icons to null

                                frame.remove(displayAnimatedImage);

                                displayAnimatedImage.setIcon(null);
                                frame.repaint();
                                // Rotate wheel image to land on Washing Machine prize
                                BufferedImage rotatedFwdImage_ = rotate(rotatedFwdImage, -170.5d);
                                ImageIcon rotatedFwdImage = new ImageIcon(rotatedFwdImage_);
                                displayAnimatedImage.setIcon(rotatedFwdImage);
                                displayAnimatedImage.setBounds(300, 255, 180, 150);
                                frame.add(displayAnimatedImage);
                            }


                            frame.repaint();
                        }

                    } catch (Exception exception) {
                        System.out.println("IMAGE NOT FOUND");
                        exception.printStackTrace();
                    }

                    if (!phrases.getPlayingPhrase().contains("_")) {
                        gameWon = true;
                        if (gameWon) {
                            int option = JOptionPane.showConfirmDialog(frame, "You solved the puzzle and won the game!\nPlay another game? (Y/N)", "Game Over", JOptionPane.YES_NO_OPTION);
                            if (option == JOptionPane.YES_OPTION) {
                                try {
                                    stopAudio();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
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

