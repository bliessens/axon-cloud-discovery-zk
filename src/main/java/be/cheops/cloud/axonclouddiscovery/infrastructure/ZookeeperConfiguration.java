package be.cheops.cloud.axonclouddiscovery.infrastructure;

import com.thoughtworks.xstream.XStream;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.distributed.*;
import org.axonframework.serialization.xml.CompactDriver;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.axonframework.springcloud.commandhandling.SpringCloudHttpBackupCommandRouter;
import org.axonframework.springcloud.commandhandling.SpringHttpCommandBusConnector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@Configuration
@Profile({"zookeeper"})
class ZookeeperConfiguration {

    private static final RoutingStrategy ROUTING_STRATEGY = new AnnotationRoutingStrategy();

//    @Bean
//    public CommandRouter springCloudCommandRouter(DiscoveryClient discoveryClient) {
//        return new SpringCloudCommandRouter(discoveryClient, ROUTING_STRATEGY);
//    }

    @Bean
    public CommandRouter springCloudHttpBackupCommandRouter(DiscoveryClient discoveryClient,
                                                            RestTemplate restTemplate,
                                                            @Value("${axon.distributed.spring-cloud.fallback-url}") String messageRoutingInformationEndpoint) {

        return new SpringCloudHttpBackupCommandRouter(discoveryClient, ROUTING_STRATEGY, restTemplate, messageRoutingInformationEndpoint);
    }

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


    static class CommandSerializerFactory {
        XStream newXStream() {
            final XStream xStream = new XStream(new CompactDriver());
            xStream.registerConverter(new XStreamLocalDateConverter());
            xStream.registerConverter(new XStreamLocalDateTimeConverter());
            return xStream;
        }
    }
}
