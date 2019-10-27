package be.cheops.cloud.axonclouddiscovery.aggr;

import org.axonframework.commandhandling.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.AnnotationCommandTargetResolver;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.common.caching.NoCache;
import org.axonframework.common.lock.PessimisticLockFactory;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.CachingEventSourcingRepository;
import org.axonframework.eventsourcing.GenericAggregateFactory;
import org.axonframework.eventsourcing.NoSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.annotation.ParameterResolverFactory;
import org.axonframework.spring.config.annotation.SpringParameterResolverFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SampleConfiguration {

    @Bean
    public AggregateFactory<SampleAggregate> sampleAggregateFactory() {
        return new GenericAggregateFactory<>(SampleAggregate.class);
    }

    @Bean(name = "sampleAggregateRepository")
    public Repository<SampleAggregate> sampleAggregateRepository(EventStore eventStore,
                                                                 AggregateFactory<SampleAggregate> sampleAggregateFactory,
                                                                 @Qualifier("springParameterResolverFactoryBean") ParameterResolverFactory parameterResolverFactory) {
        return new CachingEventSourcingRepository<>(sampleAggregateFactory, eventStore, new PessimisticLockFactory(),
                NoCache.INSTANCE, parameterResolverFactory, NoSnapshotTriggerDefinition.INSTANCE);
    }

    @Bean
    public AggregateAnnotationCommandHandler<SampleAggregate> sampleAnnotationCommandHandler(
            Repository<SampleAggregate> sampleAggregateRepository,
            @Qualifier("springParameterResolverFactoryBean") ParameterResolverFactory parameterResolverFactory) {

        return new AggregateAnnotationCommandHandler<>(SampleAggregate.class, sampleAggregateRepository, new AnnotationCommandTargetResolver(), parameterResolverFactory);
    }

    @Bean
    public SpringParameterResolverFactoryBean springParameterResolverFactoryBean() {
        return new SpringParameterResolverFactoryBean();
    }

}
