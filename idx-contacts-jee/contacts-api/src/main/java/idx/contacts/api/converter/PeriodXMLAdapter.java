package idx.contacts.api.converter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.Period;


public class PeriodXMLAdapter extends XmlAdapter<PeriodXMLAdapter.XmlPeriod, Period> {

    @Override
    public XmlPeriod marshal(Period value) throws Exception {
        if (value == null) {
            return null;
        }
        XmlPeriod period = new XmlPeriod();
        period.setYears(value.getYears());
        period.setMonths(value.getMonths());
        period.setDays(value.getDays());
        return period;
    }

    @Override
    public Period unmarshal(XmlPeriod value) throws Exception {
        if (value == null) {
            return null;
        }
        return Period.of(value.getYears(), value.getMonths(), value.getDays());
    }

    @XmlRootElement
    public static class XmlPeriod {

        private int years;
        private int months;
        private int days;


        public int getYears() {
            return years;
        }

        public void setYears(int years) {
            this.years = years;
        }

        public int getMonths() {
            return months;
        }

        public void setMonths(int months) {
            this.months = months;
        }

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }
    }
}
