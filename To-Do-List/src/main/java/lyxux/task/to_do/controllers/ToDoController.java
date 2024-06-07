package lyxux.task.to_do.controllers;

import lyxux.task.to_do.dto.ToDoDTO;
import lyxux.task.to_do.dto.ToDoTaskDTO;
import lyxux.task.to_do.services.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/v1/todos")
@RestController
@CrossOrigin
public class ToDoController {
    @Autowired
    private ToDoService toDoService;

    @GetMapping("/{userId}")
    public ResponseEntity<ToDoDTO> getToDo(@PathVariable long userId) {
        ToDoDTO toDo = toDoService.getUserToDo(userId);
        if (toDo!=null) {
            return new ResponseEntity<>(toDo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<ToDoDTO> createNewToDo(@RequestBody ToDoDTO toDoDTO) {
        ToDoDTO toDo = toDoService.createToDo(toDoDTO);
        if (toDo!=null) {
            return new ResponseEntity<>(toDo, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userId}/{toDoId}")
    public ResponseEntity<ToDoDTO> addNewTask(@PathVariable long userId, @PathVariable long toDoId, @RequestBody ToDoTaskDTO toDoTask) {
        ToDoDTO toDo = toDoService.addNewTaskToDo(userId, toDoId, toDoTask);
        if (toDo!=null) {
            return new ResponseEntity<>(toDo, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{toDoTaskId}")
    public ResponseEntity<ToDoTaskDTO> updateTaskCompletionStatus(@PathVariable long toDoTaskId, @RequestBody boolean isDone) {
        ToDoTaskDTO toDo_Task = toDoService.updateToDoStatus(toDoTaskId, isDone);
        if (toDo_Task!=null) {
            return new ResponseEntity<>(toDo_Task, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{toDoTaskID}")
    public ResponseEntity removeToDoTask(@PathVariable long toDoTaskID) {
        toDoService.deleteToDoTask(toDoTaskID);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
