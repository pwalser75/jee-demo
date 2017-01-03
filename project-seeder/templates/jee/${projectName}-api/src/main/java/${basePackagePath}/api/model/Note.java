package ${basePackage}.api.model;

import ${basePackage}.api.converter.LocalDateXMLAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;

@XmlRootElement
public class Note implements Serializable {

    private Long id;
    private String text;
    private LocalDate creationDate;
    private LocalDate modificationDate;

    public Note() {
        //
    }

    public Note(String text) {
        this.text = text;
    }

    @XmlElement(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @XmlElement(name = "creation_date")
    @XmlJavaTypeAdapter(LocalDateXMLAdapter.class)
    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @XmlElement(name = "modification_date")
    @XmlJavaTypeAdapter(LocalDateXMLAdapter.class)
    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "#" + id + ": " + text;
    }
}
