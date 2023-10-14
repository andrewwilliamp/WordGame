public class Players extends Person {

    private double money;
    private String players;

    public void setMoney(double moneyAmnt) {
        money = moneyAmnt;
    }

    public double getMoney() {
        return money;
    }


    public Players() {
        money = 1000;
    }

    @Override
    public String toString() { return "Name: " + getFirstName() + " " + getLastName() + " Money: $" + money; }

}
