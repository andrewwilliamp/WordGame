

public class Hosts extends Person {

    public int randomizeNum() {
        Numbers.generateNumber();       // Call the static method from Numbers class to generate a random number
        return Numbers.getNum();
    }


}
