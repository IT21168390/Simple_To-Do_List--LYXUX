package lyxux.task.to_do.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToDoTaskDTO {
    private long id;
    private long toDoListID;
    private String todo;
    private LocalDateTime timestamp;
    private boolean done;
}
