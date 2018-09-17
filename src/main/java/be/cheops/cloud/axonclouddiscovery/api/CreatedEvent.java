package be.cheops.cloud.axonclouddiscovery.api;

import com.fasterxml.jackson.annotation.JsonProperty;

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
