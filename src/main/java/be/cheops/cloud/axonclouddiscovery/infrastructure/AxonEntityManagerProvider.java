package be.cheops.cloud.axonclouddiscovery.infrastructure;

import org.axonframework.common.jpa.EntityManagerProvider;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//@Component
class AxonEntityManagerProvider implements EntityManagerProvider {

    @PersistenceContext(unitName = "poct-bridge")
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
