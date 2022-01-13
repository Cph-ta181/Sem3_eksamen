package entities;

import facades.OwnerFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Table(name = "Boat")
@Entity
@NamedQuery(name = "Boat.deleteAllRows", query = "DELETE from Boat")
public class Boat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private String make;
    private int year;
    private String image;
    @ManyToMany
    private List<Owner> owners = new ArrayList<>();
    @ManyToOne
    private Auction auction;


    public Boat() {
    }

    public Boat(Long id, String name, String brand, String make, int year, String image, List<Owner> owners, Auction auction) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.make = make;
        this.year = year;
        this.image = image;
        this.owners = owners;
        this.auction = auction;
    }


    public void addOwner(Owner owner){
        owners.add(owner);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }
}
