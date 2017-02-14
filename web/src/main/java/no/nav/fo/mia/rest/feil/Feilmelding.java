package no.nav.fo.mia.rest.feil;

public class Feilmelding {

    public static final String NO_BIGIP_5XX_REDIRECT = "X-Escape-5xx-Redirect";

    private String id;
    private String message;
    private String callId;

    @SuppressWarnings("unused")
    public Feilmelding() {}

    public Feilmelding(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public Feilmelding(String id, String message, String callId) {
        this(id, message);
        this.callId = callId;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }
}
