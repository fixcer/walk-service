package dev.toannv.interview.walk.repository;

import dev.toannv.interview.walk.domain.Step;
import dev.toannv.interview.walk.repository.base.IWalkRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IStepRepository extends IWalkRepository<Step, Long> {

    @Modifying
    @Query(value = "refresh materialized view daily_ranking", nativeQuery = true)
    void refreshDailyRanking();

    @Modifying
    @Query(value = "refresh materialized view weekly_ranking", nativeQuery = true)
    void refreshWeeklyRanking();

    @Modifying
    @Query(value = "refresh materialized view monthly_ranking", nativeQuery = true)
    void refreshMonthlyRanking();

}
