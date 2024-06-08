package lyxux.task.to_do.repositories;

import lyxux.task.to_do.models.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    List<ToDo> findAll();
    //List<ToDo> findAllByUserId(long id); /* If a user can create multiple To-Do lists */
    ToDo findByUserId(long id);
}
