package lyxux.task.to_do.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lyxux.task.to_do.models.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToDoDTO {
    private long id;
    private long userId;
    private List<ToDoTaskDTO> todoList;
}
