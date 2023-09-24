import java.util.Random;

public class Physical implements Award {
    private String[] physicalPrizes;
    public Physical() {
        physicalPrizes = new String[]{
                "Television",
                "Vacation Package",
                "Pellet Grill",
                "Car",
                "Washing Machine"
        };
    }

    public int getRandomPrize() {
        Random random = new Random();
        int randomIndex = random.nextInt(physicalPrizes.length);
        return randomIndex;
    }


    @Override
    public int displayWinnings(Players player, boolean correctGuess) {
        if(correctGuess) {
            int prizeIndex = getRandomPrize();
            System.out.println(player.getFirstName() + " won a " + physicalPrizes[prizeIndex]);
            return prizeIndex;
        }
        else {
            int prizeIndex = getRandomPrize();
            System.out.println(player.getFirstName() + " missed out on the chance to win a " + physicalPrizes[prizeIndex]);
            return 0;
        }

    }
}
