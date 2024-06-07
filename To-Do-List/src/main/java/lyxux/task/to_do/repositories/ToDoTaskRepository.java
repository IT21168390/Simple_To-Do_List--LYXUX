package lyxux.task.to_do.repositories;

import lyxux.task.to_do.models.ToDo;
import lyxux.task.to_do.models.ToDoTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoTaskRepository extends JpaRepository<ToDoTask, Long> {
    //ToDoItem findById(long id);
    List<ToDoTask> findByToDoListID(ToDo id);
}
