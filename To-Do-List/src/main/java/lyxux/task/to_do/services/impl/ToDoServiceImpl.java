package lyxux.task.to_do.services.impl;

import lyxux.task.to_do.dto.ToDoDTO;
import lyxux.task.to_do.dto.ToDoTaskDTO;
import lyxux.task.to_do.model_mappers.ToDoTaskDTOConverter;
import lyxux.task.to_do.models.ToDo;
import lyxux.task.to_do.models.ToDoTask;
import lyxux.task.to_do.models.User;
import lyxux.task.to_do.repositories.ToDoRepository;
import lyxux.task.to_do.repositories.ToDoTaskRepository;
import lyxux.task.to_do.repositories.UserRepository;
import lyxux.task.to_do.services.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ToDoServiceImpl implements ToDoService {
    private UserRepository userRepository;
    private ToDoRepository toDoRepository;
    private ToDoTaskRepository toDoTaskRepository;

    //private ToDoTaskDTOConverter toDoTaskDTOConverter;

    @Autowired
    public ToDoServiceImpl(UserRepository userRepository, ToDoRepository toDoRepository, ToDoTaskRepository toDoTaskRepository/*, ToDoTaskDTOConverter toDoTaskDTOConverter*/) {
        this.userRepository = userRepository;
        this.toDoRepository = toDoRepository;
        this.toDoTaskRepository = toDoTaskRepository;
        //this.toDoTaskDTOConverter = toDoTaskDTOConverter;
    }

    @Override
    public ToDoDTO getUserToDo(long userID) {
        try {
            ToDo toDo = toDoRepository.findByUserId(userID);

            ToDoDTO toDoDTO = new ToDoDTO();
            List<ToDoTaskDTO> toDoTaskDTOList = new ArrayList<>();

            if (toDo!=null) {
                if (toDo.getTodoList()!=null && !toDo.getTodoList().isEmpty()) {
                    for (ToDoTask toDoTask: toDo.getTodoList()) {
                        ToDoTaskDTO toDoTaskDTO = new ToDoTaskDTO();
                        toDoTaskDTO.setToDoListID(toDoTask.getToDoListID().getId());
                        toDoTaskDTO.setTodo(toDoTask.getTodo());
                        toDoTaskDTO.setDone(toDoTask.isDone());
                        toDoTaskDTO.setId(toDoTask.getId());
                        System.out.println("ToDo of a User after model-mapping: "+toDoTaskDTO);
                        toDoTaskDTOList.add(toDoTaskDTO);
                    }
                }

                toDoDTO.setId(toDo.getId());
                toDoDTO.setUserId(toDo.getUser().getId());
                toDoDTO.setTodoList(toDoTaskDTOList);
            } else
                toDoDTO.setId(-1);

            System.out.println("ToDo of a User after model-mapping: "+toDoDTO);
            return toDoDTO;
        } catch (NoSuchElementException nse) {
            System.out.println(nse.getMessage());
            return null;
        }
    }

    @Override
    public ToDoDTO createToDo(ToDoDTO toDoDTO) {
        ToDo newToDo = new ToDo();
        try {
            Optional<User> user = userRepository.findById(toDoDTO.getUserId());
            newToDo.setUser(user.orElseThrow());

            ToDo toDo;
            // Check whether if there is an already created To-Do for the current User
            toDo = toDoRepository.findByUserId(toDoDTO.getUserId());
            if (toDo==null) {
                toDo = toDoRepository.save(newToDo);
            }

            List<ToDoTask> toDoTaskList = new ArrayList<>();

            List<ToDoTaskDTO> incomingToDoTaskDTOList = toDoDTO.getTodoList();
            if (incomingToDoTaskDTOList != null && !toDoDTO.getTodoList().isEmpty()) {
                for (ToDoTaskDTO toDoTaskDTO: toDoDTO.getTodoList()) {
                    ToDoTask toDoTask = new ToDoTask();
                    toDoTask.setTodo(toDoTaskDTO.getTodo());
                    toDoTask.setToDoListID(toDo);

                    if (toDo.getTodoList()!=null && !toDo.getTodoList().contains(toDoTask)) {
                        ToDoTask savedToDoTask = toDoTaskRepository.save(toDoTask);

                        //toDoTask.setTimestamp(savedToDoTask.getTimestamp());
                        toDoTask.setId(savedToDoTask.getId());

                        toDoTaskDTO.setId(savedToDoTask.getId());
                    }

                    toDoTaskList.add(toDoTask);
                }
                toDo = toDoRepository.save(toDo);
                for (ToDoTaskDTO toDoTaskDTO: toDoDTO.getTodoList()) {
                    toDoTaskDTO.setToDoListID(toDo.getId());
                }
            } else
                toDoDTO.setTodoList(new ArrayList<>());

            toDoDTO.setId(toDo.getId());

            System.out.println("Newly created To-Do after conversion: "+toDoDTO);
            return toDoDTO;
        } catch (NoSuchElementException nse) {
            System.out.println(nse.getMessage());
        }
        return null;
    }

    @Override
    public ToDoDTO addNewTaskToDo(long userID, long toDo_ID, ToDoTaskDTO toDoItemDTO) {
        try {
            ToDo existingToDo = toDoRepository.findById(toDo_ID).orElseThrow();
            List<ToDoTask> existingToDoTaskList = existingToDo.getTodoList();

            ToDoTask newToDoTask = new ToDoTask();
            newToDoTask.setTodo(toDoItemDTO.getTodo());
            newToDoTask.setToDoListID(existingToDo);

            existingToDoTaskList.add(newToDoTask);
            existingToDo.setTodoList(existingToDoTaskList);

            ToDo updatedToDo = toDoRepository.save(existingToDo);
            System.out.println("When new Task is added: "+updatedToDo);

            List<ToDoTaskDTO> toDoTaskDTOList = new ArrayList<>();
            for (ToDoTask toDoTask: updatedToDo.getTodoList()) {
                ToDoTaskDTO toDoTaskDTO = new ToDoTaskDTO();
                toDoTaskDTO.setTodo(toDoTask.getTodo());
                toDoTaskDTO.setDone(toDoTask.isDone());
                toDoTaskDTO.setId(toDoTask.getId());
                toDoTaskDTO.setToDoListID(toDoTask.getToDoListID().getId());
                //toDoTaskDTO.setToDo_List_ID(toDoTask.getToDo_List_ID().getId());
                toDoTaskDTOList.add(toDoTaskDTO);
            }
            ToDoDTO toDoDTO = new ToDoDTO();
            toDoDTO.setId(updatedToDo.getId());
            toDoDTO.setUserId(updatedToDo.getUser().getId());
            toDoDTO.setTodoList(toDoTaskDTOList);
            System.out.println("When it converted: "+toDoDTO);

            return toDoDTO;
        } catch (NoSuchElementException nse) {
            System.out.println(nse.getMessage());
        }
        return null;
    }

    @Override
    public ToDoTaskDTO updateToDoStatus(long toDo_Item_ID, boolean isDone) {
        try {
            ToDoTask toDoTask = toDoTaskRepository.findById(toDo_Item_ID).get();
            toDoTask.setDone(isDone);
            ToDoTask modifiedToDoTask = toDoTaskRepository.save(toDoTask);

            ToDoTaskDTO toDoTaskDTO = new ToDoTaskDTO();
            toDoTaskDTO.setDone(modifiedToDoTask.isDone());
            toDoTaskDTO.setId(modifiedToDoTask.getId());
            toDoTaskDTO.setTodo(modifiedToDoTask.getTodo());
            toDoTaskDTO.setToDoListID(modifiedToDoTask.getToDoListID().getId());
            //toDoTaskDTO.setToDo_List_ID(modifiedToDoTask.getToDo_List_ID().getId());
            System.out.println("ToDoTask after modifying 'done' status: "+toDoTaskDTO);
            return toDoTaskDTO;
        } catch (NoSuchElementException nse) {
            System.out.println(nse.getMessage());
        }
        return null;
    }

    @Override
    public void deleteToDoTask(long toDo_Item_ID) {
        try {
            toDoTaskRepository.deleteById(toDo_Item_ID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
