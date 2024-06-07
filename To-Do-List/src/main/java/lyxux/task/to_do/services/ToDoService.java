package lyxux.task.to_do.services;

import lyxux.task.to_do.dto.ToDoDTO;
import lyxux.task.to_do.dto.ToDoTaskDTO;
import lyxux.task.to_do.models.ToDo;
import lyxux.task.to_do.models.ToDoTask;

public interface ToDoService {
    ToDoDTO getUserToDo(long userID);
    ToDoDTO createToDo(ToDoDTO toDo);
    ToDoDTO addNewTaskToDo(long userID, long toDo_ID, ToDoTaskDTO toDoItem);
    ToDoTaskDTO updateToDoStatus(long toDo_Item_ID, boolean isDone);
    void deleteToDoTask(long toDo_Item_ID);
}
