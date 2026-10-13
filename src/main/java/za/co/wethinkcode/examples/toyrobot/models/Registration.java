package za.co.wethinkcode.examples.toyrobot.models;

import java.time.LocalDate;

public class Registration {


    private int id;
    private Participant participant;
    private Workshop workshop;
    private LocalDate registered_at;

    public Registration(Participant participant, Workshop workshop){
        this.id = -1;
        this.participant = participant;
        this.workshop = workshop;
        this.registered_at = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public Participant getParticipant() {
        return participant;
    }

    public Workshop getWorkshop() {
        return workshop;
    }

    public LocalDate getRegistered_at() {
        return registered_at;
    }

    public void setId(int id) {
        if (id <= 0){
            throw new IllegalStateException("We can't accept less than zero Id");
        }
        this.id = id;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }
}
