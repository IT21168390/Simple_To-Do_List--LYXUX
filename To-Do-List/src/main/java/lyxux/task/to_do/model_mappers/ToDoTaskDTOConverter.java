package lyxux.task.to_do.model_mappers;

import lyxux.task.to_do.dto.ToDoTaskDTO;
import lyxux.task.to_do.models.ToDoTask;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ToDoTaskDTOConverter {
    @Autowired
    private ModelMapper modelMapper;

    public ToDoTaskDTO convertToDoTask(ToDoTask toDoTask) {
        ToDoTaskDTO toDoTaskDTO = modelMapper.map(toDoTask, ToDoTaskDTO.class);
        return toDoTaskDTO;
    }

    public ToDoTask convertToDoTaskDTO(ToDoTaskDTO toDoTaskDTO) {
        ToDoTask toDoTask = modelMapper.map(toDoTaskDTO, ToDoTask.class);
        return toDoTask;
    }
}
