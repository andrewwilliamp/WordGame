public class Money implements Award {
    private double winAmount;
    private double loseAmount;

    public Money(double winAmount, double loseAmount) {
        this.winAmount = winAmount;
        this.loseAmount = loseAmount;
    }

    public int displayWinnings(Players player, boolean correctGuess) {
        if(correctGuess) {
            System.out.println(player.getFirstName() + " won $" + winAmount);
            return (int) winAmount;
        }
        else {
            System.out.println(player.getFirstName() + " lost $" + loseAmount);
            return (int) -loseAmount;
        }
    }
}
