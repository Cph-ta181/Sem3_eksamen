package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "Owner")
@Entity
@NamedQuery(name = "Owner.deleteAllRows", query = "DELETE from Owner")
public class Owner implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_name"/*, length = 25*/)
    private String userName;
    private String name;
    private String phone;
    private String email;
    @ManyToMany(mappedBy = "owners")
    private List<Boat> boats;

    public Owner() {
    }

    public Owner(String userName, String name, String phone, String email, List<Boat> boats) {
        this.userName = userName;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.boats = boats;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Boat> getBoats() {
        return boats;
    }

    public void setBoats(List<Boat> boats) {
        boats = boats;
    }
}
