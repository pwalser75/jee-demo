package idx.contacts.persistence.entity;

import ch.frostnova.persistence.api.entity.BaseMetadataEntity;
import idx.contacts.api.model.Gender;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "person")
public class PersonEntity extends BaseMetadataEntity<Long> {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -1166461789114073327L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "FIRST_NAME", length = 64, nullable = false)
    private String firstName;
    @Column(name = "LAST_NAME", length = 64, nullable = false)
    private String lastName;

    @Column(name = "GENDER", length = 16)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "BIRTH_DATE")
    private LocalDate dateOfBirth;

    @Column(name = "DATE_OF_DEATH")
    private LocalDate dateOfDeath;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

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

    public LocalDate getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(LocalDate dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}
