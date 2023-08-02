package dev.toannv.interview.walk.repository;

import dev.toannv.interview.walk.domain.Step;
import dev.toannv.interview.walk.repository.base.IWalkRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStepRepository extends IWalkRepository<Step, Long> {

}
