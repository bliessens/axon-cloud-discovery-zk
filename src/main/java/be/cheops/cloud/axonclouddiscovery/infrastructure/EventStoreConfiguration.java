package be.cheops.cloud.axonclouddiscovery.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.spring.config.annotation.SpringParameterResolverFactoryBean;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
//@EnableScheduling
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "be.cegeka.vconsult.poct.readmodel.*.repositories",
//        entityManagerFactoryRef = "entityManagerFactory",
//        transactionManagerRef = "transactionManager")
public class EventStoreConfiguration {

    @Bean
    public CommandGateway commandGateway(CommandBus commandBus) {
        return new DefaultCommandGateway(commandBus);
    }

//    @Bean
//    public AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor() {
//        return new AnnotationCommandHandlerBeanPostProcessor();
//    }

    @Bean("springParameterResolverFactoryBean")
    public SpringParameterResolverFactoryBean springParameterResolverFactoryBean() {
        return new SpringParameterResolverFactoryBean();
    }

    @Bean("axonTransactionManager")
    public TransactionManager axonTransactionManager(
            @Qualifier("transactionManager") PlatformTransactionManager transactionManager) {
        return new SpringTransactionManager(transactionManager);
    }

//    @Bean
//    public JacksonSerializer eventSerializer() {
//        final ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        return new JacksonSerializer(objectMapper);
//    }
//
//    @Bean
//    public EventStorageEngine eventStorageEngine(
//            @Qualifier("axonTransactionManager") TransactionManager axonTransactionManager,
//            DataSource dataSource,
//            EntityManagerProvider entityManagerProvider,
//            JacksonSerializer jacksonSerializer) throws SQLException {
//        return new JpaEventStorageEngine(jacksonSerializer, NoOpEventUpcaster.INSTANCE, dataSource, entityManagerProvider, axonTransactionManager);
//    }
//
//    @Bean(name = "eventBus")
//    public EventStore eventStore(EventStorageEngine eventStorageEngine) {
//        return new EmbeddedEventStore(eventStorageEngine);
//    }

    @Bean
    public RestTemplate restTemplate() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return new RestTemplate(Collections.singletonList(new MappingJackson2HttpMessageConverter(mapper)));
    }

}
