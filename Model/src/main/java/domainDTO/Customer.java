package domainDTO;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.Date;

public class Customer implements IHasId<Integer>, Serializable {
    private Integer idCustomer;
    private String firstname;
    private String lastname;
    private final Date bornDate;
    private String email;
    private String phoneNumber;
    private String imageURL;

    public Customer(Integer idCustomer,String firstname, String lastname, Date bornDate, String email, String phoneNumber,String imageURL) {
        this.idCustomer = idCustomer;
        this.firstname = firstname;
        this.lastname = lastname;
        this.bornDate = bornDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.imageURL = imageURL;
    }

    public Customer(String firstname, String lastname, Date bornDate, String email, String phoneNumber){
        this.firstname = firstname;
        this.lastname = lastname;
        this.bornDate = bornDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.imageURL = "";
    }

    @Override
    public Integer getId() { return this.idCustomer; }

    @Override
    public void setId(Integer integer) { this.idCustomer = integer; }

    public String getFirstname() { return firstname; }

    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }

    public void setLastname(String lastname) { this.lastname = lastname; }

    public Date getBornDate() { return bornDate; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getImageURL() { return imageURL; }

    public void setImageURL(String imageURL) { this.imageURL = imageURL; }
}
