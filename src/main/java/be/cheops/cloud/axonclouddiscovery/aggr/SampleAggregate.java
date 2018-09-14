package be.cheops.cloud.axonclouddiscovery.aggr;

import be.cheops.cloud.axonclouddiscovery.api.CreateCommand;
import be.cheops.cloud.axonclouddiscovery.api.CreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
class SampleAggregate {

    private static final Logger LOG = LoggerFactory.getLogger(SampleAggregate.class);

    @AggregateIdentifier
    private String id;

    protected SampleAggregate() {
    }

    @CommandHandler
    public SampleAggregate(CreateCommand command) {
        LOG.info("Created new aggregate with id {}", command.getId());
        apply(new CreatedEvent(command.getId()));
    }

    @EventSourcingHandler
    public void onEvent(CreatedEvent event) {
        this.id = event.getId();
    }
}

