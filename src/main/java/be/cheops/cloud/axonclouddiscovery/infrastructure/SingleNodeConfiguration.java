package be.cheops.cloud.axonclouddiscovery.infrastructure;

import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.monitoring.NoOpMessageMonitor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"single"})
class SingleNodeConfiguration {

    @Bean("commandBus")
    public SimpleCommandBus commandBus(TransactionManager axonTransactionManager) {
        return new SimpleCommandBus(axonTransactionManager, NoOpMessageMonitor.INSTANCE);
    }

}
