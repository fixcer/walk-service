package dev.toannv.interview.walk.service.user;


import dev.toannv.interview.walk.web.api.model.GetAllUsersResponse;
import dev.toannv.interview.walk.web.api.model.GetUsersCriteria;


public interface IUserService {

    /**
     * Get all active users
     *
     * @param criteria {@link GetUsersCriteria} criteria
     * @return {@link GetAllUsersResponse} response
     */
    GetAllUsersResponse getUsers(GetUsersCriteria criteria);

}
