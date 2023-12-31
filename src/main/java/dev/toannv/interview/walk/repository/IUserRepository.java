package dev.toannv.interview.walk.repository;

import dev.toannv.interview.walk.domain.User;
import dev.toannv.interview.walk.repository.base.IWalkRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends IWalkRepository<User, Long> {

}
