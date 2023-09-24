import java.util.Scanner;

public class Hosts extends Person {


    public Hosts(String firstName) {
        setFirstName(firstName);
    }

    public void enterPhrase() {
        Scanner scnr = new Scanner(System.in);

        System.out.println(getFirstName() + ", enter a phrase for the players to guess: ");
        String phrase = scnr.nextLine();
        Phrases.setGamePhrase(phrase);

        System.out.println("The phrase has been set. Let the games begin!");
    }

    // Method to reset the gamePhrase for a new game
    public void resetGamePhrase() {
        Phrases.setGamePhrase("");
    }


}
