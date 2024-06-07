package lyxux.task.to_do.model_mappers;

import lyxux.task.to_do.dto.UserDTO;
import lyxux.task.to_do.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter {
    @Autowired
    private ModelMapper modelMapper;

    public UserDTO convertToDoTask(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    public User convertToDoTaskDTO(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return user;
    }
}
