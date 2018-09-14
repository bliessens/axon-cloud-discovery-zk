package be.cheops.cloud.axonclouddiscovery.api;

import org.codehaus.jackson.annotate.JsonProperty;

public class CreatedEvent {

    @JsonProperty("id")
    private final String id;

    public CreatedEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
