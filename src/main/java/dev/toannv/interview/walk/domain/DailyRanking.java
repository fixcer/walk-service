package dev.toannv.interview.walk.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "DAILY_RANKING")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DailyRanking implements Serializable {

    @Column(name = "step_id", nullable = false)
    private Long stepId;

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "steps", nullable = false)
    private Integer steps;

}
