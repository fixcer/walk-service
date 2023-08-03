package dev.toannv.interview.walk.repository;

import dev.toannv.interview.walk.domain.DailyRanking;
import dev.toannv.interview.walk.repository.base.IWalkRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDailyRankingRepository extends IWalkRepository<DailyRanking, Long> {

}
