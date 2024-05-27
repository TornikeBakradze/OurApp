package ge.ourApp.mappers;

import ge.ourApp.dto.UserDto;
import ge.ourApp.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToUserDto(User user);

}
