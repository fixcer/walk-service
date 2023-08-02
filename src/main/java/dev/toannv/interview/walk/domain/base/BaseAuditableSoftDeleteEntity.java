package dev.toannv.interview.walk.domain.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author ToanNV
 */
@MappedSuperclass
public abstract class BaseAuditableSoftDeleteEntity extends BaseAuditableEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "DELETED", nullable = false)
    private boolean deleted;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
