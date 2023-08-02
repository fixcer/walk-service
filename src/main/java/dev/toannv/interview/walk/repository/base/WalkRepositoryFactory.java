package dev.toannv.interview.walk.repository.base;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;

import javax.persistence.EntityManager;

/**
 * Default repo factory.
 *
 */
public class WalkRepositoryFactory extends JpaRepositoryFactory {

    /** Entity manager. */
    private final EntityManager em;

    /**
     * Constructor.
     *
     * @param entityManager
     *         Is the entity manager
     */
    public WalkRepositoryFactory(final EntityManager entityManager) {
        super(entityManager);
        em = entityManager;
    }

    @Override
    protected Class<?> getRepositoryBaseClass(final RepositoryMetadata metadata) {
        return WalkRepository.class;
    }
}
