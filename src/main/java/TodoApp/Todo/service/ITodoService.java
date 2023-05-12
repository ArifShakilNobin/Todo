package TodoApp.Todo.service;

import TodoApp.Todo.model.ToDo;

import java.util.List;

public interface ITodoService {
    List<ToDo> findAll();
    ToDo findToDoById(Integer id);
    ToDo findByToDo(String name);
    ToDo save(ToDo toDo);
    ToDo update( Integer id, ToDo toDoDetails);
    void delete(Integer id);
}
