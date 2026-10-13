package za.co.wethinkcode.examples.toyrobot.models;

import java.time.LocalDate;

public class Workshop {

    private int id;
    private String title;
    private String description;
    private LocalDate workShopDate;
    private int capacity;

    public Workshop(String title, String description, LocalDate workshopdate, int capacity){

        this.title = title;
        this.description = description;

        if (capacity < 0){
            throw new IllegalStateException("Capacity should be greater than 0 ");
        }
        if (workshopdate.isBefore(LocalDate.now())){
            throw new IllegalStateException("THe date can't be before today's date");
        }
        this.workShopDate = workshopdate;
        this.capacity = capacity;

        // Example Localdate - now -> 2026-10-13  ---- Instance 2026-01-06

    }

    public Workshop(int id ,String title, String description, LocalDate workshopdate, int capacity){

        this.title = title;
        this.description = description;

        if (capacity < 0){
            throw new IllegalStateException("Capacity should be greater than 0 ");
        }
        if (workshopdate.isBefore(LocalDate.now())){
            throw new IllegalStateException("THe date can't be before today's date");
        }

        if (id <= 0){
            throw new IllegalStateException("Should be greater than 0 please");
        }
        this.workShopDate = workshopdate;
        this.capacity = capacity;
        this.id = id;

        // Example Localdate - now -> 2026-10-13  ---- Instance 2026-01-06

    }

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public int getCapacity() {
        return capacity;
    }

    public LocalDate getWorkdate() {
        return workShopDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWorkShopDate(LocalDate workShopDate) {
        this.workShopDate = workShopDate;
    }

    public String getDescription() {
        return description;
    }
}
