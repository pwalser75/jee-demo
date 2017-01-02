package ${basePackage}.persistence.entity;

        import ${basePackage}.api.model.Gender;
        import idx.persistence.entity.BaseMetadataEntity;

        import javax.persistence.Column;
        import javax.persistence.Entity;
        import javax.persistence.GeneratedValue;
        import javax.persistence.GenerationType;
        import javax.persistence.Id;
        import javax.persistence.Table;
        import java.time.LocalDate;

@Entity
@Table(name = "person")
public class PersonEntity extends BaseMetadataEntity<Long> {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -1166461789114073327L;

    @Column(name = "first_name", unique = false, length = 64, nullable = false)
    private String firstName;
    @Column(name = "last_name", unique = false, length = 64, nullable = false)
    private String lastName;

    private Gender gender;
    private LocalDate dateOfBirth;
    private LocalDate dateOfDeath;

    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long aLong) {
        super.setId(aLong);
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
