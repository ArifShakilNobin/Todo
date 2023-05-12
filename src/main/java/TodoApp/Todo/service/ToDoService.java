package TodoApp.Todo.service;

import TodoApp.Todo.exception.ResourceNotFoundException;
import TodoApp.Todo.model.ToDo;
import TodoApp.Todo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoService implements ITodoService {

    @Autowired
    private ToDoRepository toDoRepository;


    @Override
    public List<ToDo> findAll() {
        List<ToDo> toDoList = new ArrayList<>();
        toDoList = toDoRepository.findAll();
        return toDoList;
    }

    @Override
    public ToDo findToDoById(Integer id) {
        return toDoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ToDO", "id", String.valueOf((id))));
    }

    @Override
    public ToDo findByToDo(String item) {
        ToDo toDoObj = new ToDo();
        try {
            Optional<ToDo> toDo = toDoRepository.findByItem(item);
            if (toDo.isPresent()) {
                toDoObj = toDo.get();
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return toDoObj;
    }

    @Override
    public ToDo save(ToDo toDo) {
        try {
            return toDoRepository.save(toDo);
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    @Override
    public ToDo update(Integer id, ToDo toDoDetails) {
        ToDo toDo = toDoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ToDO", "id", String.valueOf((id))));

        toDo.setItem(toDoDetails.getItem());
        toDo.setDescription(toDoDetails.getDescription());
        toDo.setDate(toDoDetails.getDate());
        ToDo updatedToDo = toDoRepository.save(toDo);
        return updatedToDo;
    }

    @Override
    public void delete(Integer id) {
        try {
            if (id != null) {
                toDoRepository.deleteById(id);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
