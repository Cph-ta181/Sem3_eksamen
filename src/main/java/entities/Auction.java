package entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "Auction")
@Entity
@NamedQuery(name = "Auction.deleteAllRows", query = "DELETE from Auction")
public class Auction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Date date;
    private String location;
    @OneToMany(mappedBy = "auction")
    private List<Boat> boats;


    public Auction(){
    }

    public Auction(long id, String name, Date date, String location, List<Boat> boats) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
        this.boats = boats;
    }

    public Auction(String name, Date date, String location, List<Boat> boats) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.boats = boats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Boat> getBoats() {
        return boats;
    }

    public void setBoats(List<Boat> boats) {
        this.boats = boats;
    }
}
