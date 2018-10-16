package pl.damiankotynia.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Service implements Serializable {
    private int id;
    private String name;
    private LocalDateTime time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
