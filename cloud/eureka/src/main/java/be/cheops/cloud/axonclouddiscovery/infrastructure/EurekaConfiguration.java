package be.cheops.cloud.axonclouddiscovery.infrastructure;

import com.thoughtworks.xstream.XStream;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.distributed.*;
import org.axonframework.serialization.xml.CompactDriver;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.axonframework.springcloud.commandhandling.SpringCloudCommandRouter;
import org.axonframework.springcloud.commandhandling.SpringHttpCommandBusConnector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestOperations;

@EnableEurekaClient
@Configuration
@Profile({"eureka"})
class EurekaConfiguration {

    private static final RoutingStrategy ROUTING_STRATEGY = new AnnotationRoutingStrategy();

    @Bean
    public CommandRouter springCloudCommandRouter(DiscoveryClient discoveryClient) {
        return new SpringCloudCommandRouter(discoveryClient, ROUTING_STRATEGY);
    }

//    @Bean
//    public CommandRouter springCloudHttpBackupCommandRouter(DiscoveryClient discoveryClient,
//                                                            RestTemplate restTemplate,
//                                                            @Value("${axon.distributed.spring-cloud.fallback-url}") String messageRoutingInformationEndpoint) {
//
//        return new SpringCloudHttpBackupCommandRouter(discoveryClient, ROUTING_STRATEGY, restTemplate, messageRoutingInformationEndpoint);
//    }

    @Bean
    public CommandBusConnector springHttpCommandBusConnector(@Qualifier("localSegment") CommandBus localSegment,
                                                             RestOperations restTemplate) {
        final CommandSerializerFactory serializerFactory = new CommandSerializerFactory();

        return new SpringHttpCommandBusConnector(localSegment, restTemplate, new XStreamSerializer(serializerFactory.newXStream()));
    }

    @Bean("distributedCommandBus")
    @Primary
    public DistributedCommandBus distributedCommandBus(CommandRouter commandRouter,
                                                       CommandBusConnector commandBusConnector) {
        return new DistributedCommandBus(commandRouter, commandBusConnector);
    }

//    @Bean({"localCommandBus", "localSegment"})
//    public SimpleCommandBus commandBus(TransactionManager axonTransactionManager) {
//        return new SimpleCommandBus(axonTransactionManager, NoOpMessageMonitor.INSTANCE);
//    }

    static class CommandSerializerFactory {
        XStream newXStream() {
            return new XStream(new CompactDriver());
        }
    }
}
