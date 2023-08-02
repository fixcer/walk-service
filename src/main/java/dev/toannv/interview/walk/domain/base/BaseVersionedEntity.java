package dev.toannv.interview.walk.domain.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Base entity of all classes which support optimistic locking mechanism (contains column VERSION).
 *
 * @author ToanNV
 */
@MappedSuperclass
public abstract class BaseVersionedEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Version
    @Column(name = "VERSION", nullable = false)
    private int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
