package TodoApp.Todo.repository;

import TodoApp.Todo.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo,Integer> {
    Optional<ToDo> findByItem(String item);
}
