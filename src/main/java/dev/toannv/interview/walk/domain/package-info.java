@QueryEntities(value = {
        BaseEntity.class,
        BaseVersionedEntity.class,
        BaseAuditableEntity.class,
        BaseAuditableSoftDeleteEntity.class
})
package dev.toannv.interview.walk.domain;

import com.querydsl.core.annotations.QueryEntities;
import dev.toannv.interview.walk.domain.base.BaseAuditableEntity;
import dev.toannv.interview.walk.domain.base.BaseAuditableSoftDeleteEntity;
import dev.toannv.interview.walk.domain.base.BaseEntity;
import dev.toannv.interview.walk.domain.base.BaseVersionedEntity;

