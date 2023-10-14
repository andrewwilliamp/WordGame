import java.util.Scanner;

public class Hosts extends Person {


    public Hosts(String firstName) {
        setFirstName(firstName);
    }

    public void enterPhrase() {
        Scanner scnr = new Scanner(System.in);
        String phrase = scnr.nextLine();
        Phrases.setGamePhrase(phrase);
    }

    // Method to reset the gamePhrase for a new game
    public void resetGamePhrase() {
        Phrases.setGamePhrase("");
    }


}
