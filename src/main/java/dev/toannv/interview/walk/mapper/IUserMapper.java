package dev.toannv.interview.walk.mapper;

import dev.toannv.interview.walk.domain.User;
import dev.toannv.interview.walk.web.api.model.UserResponseItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

    @Mapping(source = "id", target = "userId")
    UserResponseItem toUserResponseItem(User user);
}
