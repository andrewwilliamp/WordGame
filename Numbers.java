
import java.util.Random;


public class Numbers {
    private static Random rand = new Random();
    private static int randomNum;

    public static void setNum(int num) {
        randomNum = num;
    }

    public static int getNum() {
        return randomNum;
    }

    public static void generateNumber() {
        randomNum = rand.nextInt(2);
        setNum(randomNum);
    }

    public static boolean compareNumber(int guess) {
        if(guess == randomNum) {
            return true;
        }
        else if(guess > randomNum) {
            return false;
        }
        else {
            return false;
        }
    }

}
