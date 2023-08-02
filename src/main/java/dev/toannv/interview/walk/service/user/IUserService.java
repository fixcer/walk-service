package dev.toannv.interview.walk.service.user;


import dev.toannv.interview.walk.domain.User;
import dev.toannv.interview.walk.web.api.model.GetAllUsersResponse;
import dev.toannv.interview.walk.web.api.model.GetUsersCriteria;


public interface IUserService {

    /**
     * Check user exist by id
     *
     * @param userId {@link Long} the user id
     * @return {@link Boolean} true if existed, false if not exist
     */
    boolean existedById(Long userId);

    /**
     * Find user by id
     *
     * @param id {@link Long} the user id
     * @return {@link User} user, or null if not found
     */
    User findById(Long id);

    /**
     * Get all active users
     *
     * @param criteria {@link GetUsersCriteria} criteria
     * @return {@link GetAllUsersResponse} response
     */
    GetAllUsersResponse getUsers(GetUsersCriteria criteria);

}
