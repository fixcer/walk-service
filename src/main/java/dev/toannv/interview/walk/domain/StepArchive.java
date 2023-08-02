package dev.toannv.interview.walk.domain;

import dev.toannv.interview.walk.domain.base.BaseAuditableSoftDeleteEntity;
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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;


@Entity
@Builder
@Table(name = "STEP_ARCHIVES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "STEP_ARCHIVE_ID", insertable = false, updatable = false))
public class StepArchive extends BaseAuditableSoftDeleteEntity {

    @Column(name = "step_id", nullable = false)
    private Long stepId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "steps", nullable = false)
    @Min(0)
    private Integer steps;

}
