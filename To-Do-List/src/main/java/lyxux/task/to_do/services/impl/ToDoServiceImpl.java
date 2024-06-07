package lyxux.task.to_do.services.impl;

import lyxux.task.to_do.dto.ToDoDTO;
import lyxux.task.to_do.dto.ToDoTaskDTO;
import lyxux.task.to_do.model_mappers.ToDoTaskDTOConverter;
import lyxux.task.to_do.model_mappers.UserDTOConverter;
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

    private ToDoTaskDTOConverter toDoTaskDTOConverter;
    private UserDTOConverter userDTOConverter;

    @Autowired
    public ToDoServiceImpl(UserRepository userRepository, ToDoRepository toDoRepository, ToDoTaskRepository toDoTaskRepository, ToDoTaskDTOConverter toDoTaskDTOConverter, UserDTOConverter userDTOConverter) {
        this.userRepository = userRepository;
        this.toDoRepository = toDoRepository;
        this.toDoTaskRepository = toDoTaskRepository;
        this.toDoTaskDTOConverter = toDoTaskDTOConverter;
        this.userDTOConverter = userDTOConverter;
    }

    @Override
    public ToDoDTO getUserToDo(long userID) {
        try {
            ToDo toDo = toDoRepository.findByUserId(userID);
            List<ToDoTaskDTO> toDoTaskDTOList = new ArrayList<>();
            for (ToDoTask toDoTask: toDo.getTodoList()) {
                ToDoTaskDTO toDoTaskDTO = toDoTaskDTOConverter.convertToDoTask(toDoTask);
                toDoTaskDTO.setToDoListID(toDoTask.getToDoListID().getId());

                toDoTaskDTOList.add(toDoTaskDTO);
            }
            ToDoDTO toDoDTO = new ToDoDTO();
            toDoDTO.setId(toDo.getId());
            toDoDTO.setUserId(toDo.getUser().getId());
            toDoDTO.setTodoList(toDoTaskDTOList);

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

            ToDo toDo = toDoRepository.save(newToDo);

            List<ToDoTask> toDoTaskList = new ArrayList<>();
            if (!toDoDTO.getTodoList().isEmpty()) {
                for (ToDoTaskDTO toDoTaskDTO: toDoDTO.getTodoList()) {
                    ToDoTask toDoTask = toDoTaskDTOConverter.convertToDoTaskDTO(toDoTaskDTO);
                    toDoTaskList.add(toDoTask);
                }
                toDo = toDoRepository.save(toDo);
            }

            for (ToDoTaskDTO toDoTaskDTO: toDoDTO.getTodoList()) {
                toDoTaskDTO.setToDoListID(toDo.getId());
            }
            /*ToDoDTO createdToDoDTO = new ToDoDTO();
            createdToDoDTO.setId(toDo.getId());
            createdToDoDTO.setUserId(toDo.getUser().getId());*/
            toDoDTO.setId(toDo.getId());

            System.out.println("Newly created To-Do: "+toDo);
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

            ToDoTask newToDoTask = toDoTaskDTOConverter.convertToDoTaskDTO(toDoItemDTO);
            existingToDoTaskList.add(newToDoTask);
            existingToDo.setTodoList(existingToDoTaskList);

            ToDo updatedToDo = toDoRepository.save(existingToDo);
            System.out.println("When new Task is added: "+updatedToDo);

            List<ToDoTaskDTO> toDoTaskDTOList = new ArrayList<>();
            for (ToDoTask toDoTask: updatedToDo.getTodoList()) {
                ToDoTaskDTO toDoTaskDTO = toDoTaskDTOConverter.convertToDoTask(toDoTask);
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

            ToDoTaskDTO toDoTaskDTO = toDoTaskDTOConverter.convertToDoTask(modifiedToDoTask);
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
