package dev.toannv.interview.walk.repository;

import dev.toannv.interview.walk.domain.User;
import dev.toannv.interview.walk.repository.base.IWalkRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends IWalkRepository<User, Long> {

    boolean existsByIdAndDeletedIsFalse(Long userId);

    List<User> findAllByDeletedIsFalse();
}
