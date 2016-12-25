package idx.contacts.api.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.text.Collator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Provides information about the currently logged in user
 */
@XmlRootElement
public class UserInfo implements Serializable {

    private String principalName;
    private Set<String> roles = new HashSet<>();

    public UserInfo() {
        //
    }

    @XmlElement(name = "principalName")
    public String getPrincipalName() {
        return principalName;
    }

    @XmlElement(name = "roles")
    public Set<String> getRoles() {
        return roles;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public void setRoles(Set<String> roles) {
        this.roles.clear();
        if (roles != null) {
            this.roles.addAll(roles);
        }
    }

    @Override
    public String toString() {
        return principalName + " (" + roles.stream().sorted(Collator.getInstance()).collect(Collectors.joining(",")) + ")";
    }
}
