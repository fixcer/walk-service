package dev.toannv.interview.walk.web.rest;

import dev.toannv.interview.walk.service.user.IUserService;
import dev.toannv.interview.walk.web.api.UserV1ApiDelegate;
import dev.toannv.interview.walk.web.api.model.GetAllUsersResponse;
import dev.toannv.interview.walk.web.api.model.GetUsersCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserV1Controller implements UserV1ApiDelegate {

    private final IUserService userService;

    @Override
    public ResponseEntity<GetAllUsersResponse> getAllUsers(final GetUsersCriteria criteria) {
        return ResponseEntity.ok(userService.getUsers(criteria));
    }

}
