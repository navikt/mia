package no.nav.fo.mia.rest.feil;

import no.nav.modig.core.exception.ModigException;
import no.nav.modig.common.MDCOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.*;
import static no.nav.fo.mia.rest.feil.Feilmelding.NO_BIGIP_5XX_REDIRECT;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<ModigException> {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionMapper.class);

    @Override
    public Response toResponse(ModigException e) {
        String callId = MDCOperations.getFromMDC(MDCOperations.MDC_CALL_ID);
        ResponseBuilder response;
        response = serverError().header(NO_BIGIP_5XX_REDIRECT, true);
        logger.error(String.format("REST-kall feilet %s", callId), e);

        return response.type(APPLICATION_JSON).entity(new Feilmelding(e.getId(), e.getMessage(), callId)).build();
    }
}
