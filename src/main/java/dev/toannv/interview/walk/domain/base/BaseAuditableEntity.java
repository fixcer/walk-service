package dev.toannv.interview.walk.domain.base;

import dev.toannv.interview.walk.utils.Constants;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author ToanNV
 */
@MappedSuperclass
public abstract class BaseAuditableEntity extends BaseVersionedEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Callback executed onSave or onFlushDirty events.
     */
    @PrePersist
    @PreUpdate
    public void onSave() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final String author = Objects.nonNull(securityContext.getAuthentication())
                ? securityContext.getAuthentication().getName()
                : Constants.SYSTEM_ACCOUNT;

        if (!isPersisted()) {
            setCreatedAt(LocalDateTime.now());
            setCreatedBy(author);
        }

        setUpdatedAt(LocalDateTime.now());
        setUpdatedBy(author);
    }
}
