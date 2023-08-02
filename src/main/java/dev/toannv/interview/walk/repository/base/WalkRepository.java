package dev.toannv.interview.walk.repository.base;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.FactoryExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.repository.core.EntityInformation;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

/**
 * Base repository for all entities in backend services.
 *
 */
public class WalkRepository<T, ID extends Serializable> extends QuerydslJpaRepository<T, ID>
        implements IWalkRepository<T, ID> {

    private final EntityPath<T> path;
    private final PathBuilder<T> builder;
    private final Querydsl querydsl;
    private EntityInformation<T, ID> entityInfo;
    private EntityManager entityManager;

    /**
     * @param entityInformation the entity information
     * @param entityManager     the entity manager
     */
    public WalkRepository(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        this(entityInformation, entityManager, SimpleEntityPathResolver.INSTANCE);

        this.entityInfo = entityInformation;
        this.entityManager = entityManager;
    }

    /**
     * Constructor.
     *
     * @param entityInformation entity information
     * @param entityManager     entity manager
     * @param resolver          entity path resolver
     */
    public WalkRepository(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager,
                          EntityPathResolver resolver) {

        super(entityInformation, entityManager, resolver);
        this.path = resolver.createPath(entityInformation.getJavaType());
        this.builder = new PathBuilder<>(path.getType(), path.getMetadata());
        this.querydsl = new Querydsl(entityManager, builder);
    }

    @Override
    public Class<T> getDomainClass() {
        return entityInfo.getJavaType();
    }

    /**
     * @return Returns the querydsl.
     */
    public Querydsl getQuerydsl() {
        return querydsl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> findAll(JPAQuery<T> query) {
        return query.clone(entityManager).select(path).fetch();
    }

    /**
     * {@inheritDoc}
     */

    @SuppressWarnings("unchecked")
    @Override
    public <DTO> List<DTO> findAll(JPAQuery query, FactoryExpression<DTO> factoryExpression) {
        if (factoryExpression != null) {
            return query.clone(entityManager).select(factoryExpression).fetch();
        }
        return query.clone(entityManager).select(path).fetch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T findOne(JPAQuery<T> query) {
        return query.clone(entityManager).select(path).fetchOne();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count(JPAQuery<T> query) {
        return query.clone(entityManager).fetchCount();
    }
}
