package be.cheops.cloud.axonclouddiscovery.rest;

import be.cheops.cloud.axonclouddiscovery.api.CreateCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.UUID;

@EnableWebMvc
@RestController
public class AggregateController {

    private final CommandGateway gateway;

    @Autowired
    public AggregateController(CommandGateway gateway) {
        this.gateway = gateway;
    }

    @RequestMapping(path = "/aggregate", method = RequestMethod.POST)
    public ResponseEntity<String> createAggregate() {

        final String id = gateway.sendAndWait(new CreateCommand(UUID.randomUUID().toString()));

        return ResponseEntity.ok(id);
    }

    @RequestMapping(path = "/aggregate", method = RequestMethod.GET)
    public ResponseEntity<String> readsome() {

        return ResponseEntity.ok(UUID.randomUUID().toString());
    }
}
