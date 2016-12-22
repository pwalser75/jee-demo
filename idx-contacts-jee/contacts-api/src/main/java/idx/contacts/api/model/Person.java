package idx.contacts.api.model;

import idx.contacts.api.converter.LocalDateXMLAdapter;
import idx.contacts.api.converter.PeriodXMLAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

@XmlRootElement
public class Person implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dateOfBirth;

    public Person() {
        //
    }

    public Person(String firstName, String lastName, Gender gender, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    @XmlElement(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @XmlElement(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @XmlElement(name = "gender")
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @XmlElement(name = "birth_date")
    @XmlJavaTypeAdapter(LocalDateXMLAdapter.class)
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    // synthetic methods

    @XmlElement(name = "age")
    @XmlJavaTypeAdapter(PeriodXMLAdapter.class)
    public Period getAge() {
        if (dateOfBirth == null) {
            return null;
        }
        return Period.between(dateOfBirth, (LocalDate.now()));
    }

    public void setAge(Period age) {
        // ignore, it's a read-only property, which might get submitted back from REST clients
    }


    @Override
    public String toString() {
        return getClass().getSimpleName() + "#" + id;
    }
}
