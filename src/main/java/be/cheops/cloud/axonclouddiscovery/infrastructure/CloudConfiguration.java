package be.cheops.cloud.axonclouddiscovery.infrastructure;

import com.thoughtworks.xstream.XStream;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.distributed.AnnotationRoutingStrategy;
import org.axonframework.commandhandling.distributed.CommandBusConnector;
import org.axonframework.commandhandling.distributed.CommandRouter;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.monitoring.NoOpMessageMonitor;
import org.axonframework.serialization.xml.CompactDriver;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.axonframework.springcloud.commandhandling.SpringCloudCommandRouter;
import org.axonframework.springcloud.commandhandling.SpringHttpCommandBusConnector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestOperations;

@EnableDiscoveryClient
@Configuration
@Profile({"!single", "cloud"})
class CloudConfiguration {

    @Bean
    public CommandRouter springCloudCommandRouter(DiscoveryClient discoveryClient) {
        return new SpringCloudCommandRouter(discoveryClient, new AnnotationRoutingStrategy());
    }

    @Bean
    public CommandBusConnector springHttpCommandBusConnector(@Qualifier("localCommandBus") CommandBus localSegment,
                                                             RestOperations restOperations) {
        final CommandSerializerFactory serializerFactory = new CommandSerializerFactory();

        return new SpringHttpCommandBusConnector(localSegment, restOperations, new XStreamSerializer(serializerFactory.newXStream()));
    }

    @Bean("distributedCommandBus")
    @Primary
    public DistributedCommandBus distributedCommandBus(CommandRouter commandRouter,
                                                       CommandBusConnector commandBusConnector) {
        return new DistributedCommandBus(commandRouter, commandBusConnector);
    }

    @Bean("localCommandBus")
    public SimpleCommandBus commandBus(TransactionManager axonTransactionManager) {
        return new SimpleCommandBus(axonTransactionManager, NoOpMessageMonitor.INSTANCE);
    }

    static class CommandSerializerFactory {
        XStream newXStream() {
            final XStream xStream = new XStream(new CompactDriver());
            xStream.registerConverter(new XStreamLocalDateConverter());
            xStream.registerConverter(new XStreamLocalDateTimeConverter());
            return xStream;
        }
    }
}
