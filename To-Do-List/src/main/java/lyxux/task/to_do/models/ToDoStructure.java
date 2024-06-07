package lyxux.task.to_do.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDoStructure {
    private String todo;
    private boolean done;
}
