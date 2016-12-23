package idx.ws.client.util;

import idx.ws.client.util.exception.GenericServerExceptinon;
import idx.ws.client.util.exception.UnauthorizedException;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.xml.ws.http.HTTPException;

/**
 * Created by pwalser on 23.12.2016.
 */
public final class ResponseExceptionMapper {

    private ResponseExceptionMapper() {

    }

    public static Response check(Response response, int... expected) throws HTTPException {

        check(response);
        for (int ex : expected) {
            if (response.getStatus() == ex) {
                return response;
            }
        }
        throw new IllegalStateException("Unexpected return status: " + response.getStatus());
    }

    public static Response check(Response response) throws HTTPException {
        if (response == null) {
            throw new NullPointerException("response must not be null");
        }
        int status = response.getStatus();
        if (status >= 400) {
            if (status == 400) {
                throw new BadRequestException(response.readEntity(String.class));
            }
            if (status == 401) {
                throw new UnauthorizedException(response.readEntity(String.class));
            }
            if (status == 403) {
                throw new ForbiddenException(response.readEntity(String.class));
            }
            if (status == 404) {
                throw new NotFoundException(response.readEntity(String.class));
            }
            if (status == 405) {
                throw new NotAllowedException(response.readEntity(String.class));
            }
            if (status == 500) {
                throw new InternalServerErrorException(response.readEntity(String.class));
            }
            throw new GenericServerExceptinon(status, response.readEntity(String.class));
        }

        return response;
    }
}
