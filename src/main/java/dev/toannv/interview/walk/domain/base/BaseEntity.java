package dev.toannv.interview.walk.domain.base;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.proxy.HibernateProxyHelper;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * Base class for all ChefAI Backend entities.
 * <br />
 *
 * @author ToanNV
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * A special entity ID for transient one (i.e. this entity hasn't been persisted).
     */
    public static final long TRANSIENT_ENTITY_ID = -1;

    /**
     * Has the hashCode value been leaked while being in transient state? e.g. hashcode is asking twice on the same
     * object: one when the object is being in transient state (not saved) and one when it is saved.
     */
    @Transient
    private boolean transientHashCodeLeaked = false;

    @Id
    @GeneratedValue(generator = "snowflake")
    @GenericGenerator(name = "snowflake", strategy = "dev.toannv.interview.walk.utils.Snowflake")
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Check if this entity has not been persisted yet.
     *
     * @return true or false
     */
    @Transient
    public boolean isPersisted() {
        return this.id != null && this.id > TRANSIENT_ENTITY_ID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        final Class<?> thisClass = HibernateProxyHelper.getClassWithoutInitializingProxy(this);
        final Class<?> otherClass = HibernateProxyHelper.getClassWithoutInitializingProxy(obj);
        if (thisClass != otherClass) {
            return false;
        }

        if (obj instanceof BaseEntity) {
            final BaseEntity other = (BaseEntity) obj;
            if (isPersisted() && other.isPersisted()) {
                return new EqualsBuilder().append(id, other.id).isEquals();
            }
            // if one of entity is new (transient), they are considered not equal.
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (isPersisted()) { // is new or is in transient state.
            transientHashCodeLeaked = true;
            //@sonarqube:off
            return -super.hashCode();       //NOSONAR
            //@sonarqube:on
        }

        // because hashcode has just been asked for when the object is in transient state at that time,
        // super.hashCode(); is returned. Now for consistency, we return the same value.
        if (transientHashCodeLeaked) {
            //@sonarqube:off
            return -super.hashCode();       //NOSONAR
            //@sonarqube:on
        }
        return new HashCodeBuilder().append(id).toHashCode();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + id + ")";
    }
}
