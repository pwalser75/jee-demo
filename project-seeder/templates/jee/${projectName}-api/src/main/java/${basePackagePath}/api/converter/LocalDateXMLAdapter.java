package ${basePackage}.api.converter;

        import javax.xml.bind.annotation.adapters.XmlAdapter;
        import java.time.LocalDate;

public class LocalDateXMLAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public String marshal(LocalDate value) throws Exception {
        return value == null ? null : value.toString();
    }

    @Override
    public LocalDate unmarshal(String value) throws Exception {
        return value == null ? null : LocalDate.parse(value);
    }
}
