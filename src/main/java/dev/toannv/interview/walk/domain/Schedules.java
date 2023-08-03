package dev.toannv.interview.walk.domain;

import dev.toannv.interview.walk.domain.base.BaseAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;


@Entity
@Builder
@Table(name = "SCHEDULES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "SCHEDULE_ID", insertable = false, updatable = false))
public class Schedules extends BaseAuditableEntity {

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "next_run_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date nextRunAt;

}
