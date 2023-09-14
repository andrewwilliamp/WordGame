public class Person {

    private String firstName;
    private String lastName;

    public void setFirstName(String first) {
        firstName = first;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String last) {
        lastName = last;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstNoLast(String fName) {
        firstName = fName;
        lastName = "";
    }

    public void setFirstAndLast(String name, String lName) {
        firstName = name;
        lastName = lName;
    }


}

