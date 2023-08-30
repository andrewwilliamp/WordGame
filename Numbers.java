
 import java.util.Random;


public class Numbers {
    Random rand = new Random();
    private int randomNum;

    public void setNum(int num) {
        randomNum = num;
    }

    public int getNum() {
        return randomNum;
    }

    public void generateNumber() {
        randomNum = rand.nextInt(100);
        setNum(randomNum);
    }

    public boolean compareNumber(int guess) {
        if(guess == randomNum) {
            System.out.println("Congratulations, you guessed the number!");
            return true;
        }
        else if(guess > randomNum) {
            System.out.println("I'm sorry.  That guess was too high.");
            return false;
        }
        else {
            System.out.println("I'm sorry.  That guess was too low.");
            return false;
        }
    }

}
