package be.cheops.cloud.axonclouddiscovery.infrastructure;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"single"})
class SingleNodeConfiguration {

//    @Bean("commandBus")
//    public SimpleCommandBus commandBus(TransactionManager axonTransactionManager) {
//        return new SimpleCommandBus(axonTransactionManager, NoOpMessageMonitor.INSTANCE);
//    }

}
