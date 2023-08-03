package dev.toannv.interview.walk.repository;

import dev.toannv.interview.walk.domain.Schedules;
import dev.toannv.interview.walk.repository.base.IWalkRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IScheduleRepository extends IWalkRepository<Schedules, Long> {

    Schedules findByCode(String code);

}
