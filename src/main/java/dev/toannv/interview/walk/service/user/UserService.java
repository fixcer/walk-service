package dev.toannv.interview.walk.service.user;


import com.querydsl.core.BooleanBuilder;
import dev.toannv.interview.walk.domain.QUser;
import dev.toannv.interview.walk.domain.User;
import dev.toannv.interview.walk.mapper.IUserMapper;
import dev.toannv.interview.walk.repository.IUserRepository;
import dev.toannv.interview.walk.utils.Constants;
import dev.toannv.interview.walk.web.api.model.GetAllUsersResponse;
import dev.toannv.interview.walk.web.api.model.GetUsersCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Override
    public GetAllUsersResponse getUsers(final GetUsersCriteria criteria) {
        BooleanBuilder query = new BooleanBuilder();

        query.and(QUser.user.deleted.isFalse());
        Pageable pageable = PageRequest.of(
                criteria.getPage() - 1,
                criteria.getSize(),
                Sort.by(Constants.SortField.UPDATED_AT).descending());
        Page<User> userPage = userRepository.findAll(query, pageable);

        GetAllUsersResponse response = new GetAllUsersResponse();
        response.setTotal(userPage.getTotalElements());
        response.setPage(criteria.getPage());
        response.setSize(criteria.getSize());
        response.setUsers(CollectionUtils.emptyIfNull(userPage.getContent()).stream()
                .map(IUserMapper.INSTANCE::toUserResponseItem)
                .toList());
        return response;
    }

}
