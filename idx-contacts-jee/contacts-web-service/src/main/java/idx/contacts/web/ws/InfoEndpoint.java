package idx.contacts.web.ws;

import idx.contacts.api.model.UserInfo;
import idx.contacts.api.service.ContactService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Full local path: https://localhost:8443/contacts/api/contacts
 *
 * @author pwalser
 */
@RequestScoped
@Path("info")
public class InfoEndpoint {

    @Context
    SecurityContext securityContext;

    @Inject
    private ContactService contactService;

    /**
     * Get user info
     *
     * @return user info (principal and roles)
     */
    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public UserInfo getUserInfo() {

        UserInfo userInfo = new UserInfo();
        if (securityContext != null) {
            Principal principal = securityContext.getUserPrincipal();

            if (principal != null) {
                userInfo.setPrincipalName(principal.getName());
                //TODO: set roles
            }
        }

        return userInfo;
    }
}
