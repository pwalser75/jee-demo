package idx.persistence.jpa.example;

import idx.persistence.entity.BaseMetadataEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "person")
public class Person extends BaseMetadataEntity<Long> {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -1166461789114073327L;

    private String emailAddress;

    private String firstName;

    private String lastName;

    private Gender gender;
    private LocalDate dateOfBirth;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return super.getId();
    }

    @Column(name = "email_address", unique = true, length = 64, nullable = false)
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Column(name = "first_name", length = 16, nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", length = 16, nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}
