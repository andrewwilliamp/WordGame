public class Phrases {

    public static String gamePhrase;        // The chosen phrase
    public static String playingPhrase;     // Phrase with underscores


    public Phrases() {
    }

    public static void setGamePhrase(String phrase) {
        gamePhrase = phrase;
        initializePlayingPhrase();
    }

    public static String getGamePhrase() {
        return gamePhrase;
    }

    public static String getPlayingPhrase() {
        return playingPhrase;
    }

    // Initialize playingPhrase with underscores
    public static void initializePlayingPhrase() {
        StringBuilder builder = new StringBuilder();
        // for each character in gamePhrase, add an underscore to builder object while preserving spaces
        for (char c : gamePhrase.toCharArray()) {
            if (Character.isLetter(c)) {
                builder.append('_');
            } else {
                builder.append(c);
            }
            builder.append(' ');
        }
        playingPhrase = builder.toString().trim();
    }

    public static boolean findLetters(String letter) throws MultipleLettersException {
        if (letter.length() > 1) {
            throw new MultipleLettersException();
        }

        char charToFind = letter.charAt(0);
        char[] gamePhraseChars = gamePhrase.toCharArray();
        char[] playingPhraseChars = playingPhrase.toCharArray();

        boolean found = false;
        for (int i = 0; i < gamePhraseChars.length; ++i) {
            if (Character.toUpperCase(gamePhraseChars[i]) == Character.toUpperCase(charToFind)) {
                playingPhraseChars[i * 2] = gamePhraseChars[i];
                found = true;
            }
        }

        playingPhrase = new String(playingPhraseChars);

        System.out.println((found ? "Yes, that letter is in the phrase!" :
                "The letter '" + letter + "' is not in the phrase."));

        return found; // Return whether the guess was correct or not
    }
}

