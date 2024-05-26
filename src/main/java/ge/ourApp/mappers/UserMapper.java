package ge.ourApp.mappers;

import ge.ourApp.dto.UserDto;
import ge.ourApp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);

}
