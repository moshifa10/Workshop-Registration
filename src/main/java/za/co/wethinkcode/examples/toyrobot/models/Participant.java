package za.co.wethinkcode.examples.toyrobot.models;

public class Participant {

    private int id = -1;
    private String fullName;
    private String email;

    public Participant(String fullName, String email){
        this.fullName = fullName;
        this.email = email;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setId(int id) {
        if(id <= 0){
            throw new IllegalArgumentException("the ID should be over 0");
        }
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Your id: " +  this.id + " Your fullname "+ this.fullName + " email: "+ this.email;
    }
}
