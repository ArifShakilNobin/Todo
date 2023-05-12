package TodoApp.Todo.controller;

import TodoApp.Todo.exception.ErrorResponse;
import TodoApp.Todo.model.ToDo;
import TodoApp.Todo.payload.ApiResponse;
import TodoApp.Todo.service.ITodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class ToDoController {
    @Autowired
    private ITodoService iTodoService;

    @GetMapping("/todos")
    public ResponseEntity<ApiResponse> getAllTask() {
        List<ToDo> toDos = iTodoService.findAll();
        System.out.println("getting data from DB :" + toDos);
        return ResponseEntity.ok(new ApiResponse(true, "ToDO List Retrieved Successfully", toDos));
    }

    @PostMapping("/todo")
    public ResponseEntity<ApiResponse> createTask(@RequestBody ToDo toDo) {
        ToDo existingToDo = iTodoService.findByToDo(toDo.getItem());
        if (existingToDo != null) {
            return new ResponseEntity(new ErrorResponse("ToDo Already Exists ! Please try again..", null),
                    HttpStatus.BAD_REQUEST);
        }
        ToDo createdToDo = iTodoService.save(toDo);
        if (Objects.nonNull(createdToDo)){
            return ResponseEntity.ok(new ApiResponse(true, "ToDO Saved Successfully", createdToDo));
        }
        return new ResponseEntity(new ErrorResponse("Could not save todo ! Please try again..", null),
                HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/todo/{id}")
    public ResponseEntity<ApiResponse> getTaskById(@PathVariable Integer id) {
        String message = "ToDo Loaded Successfully !";
        boolean successFlag = true;
        ToDo toDo = iTodoService.findToDoById(id);
        if (toDo == null) {
            message = "No Data Found!!";
            successFlag = false;
        }
        return new ResponseEntity(new ApiResponse(successFlag, message, toDo), HttpStatus.OK);
    }


    @PutMapping("/todo/{id}")
    public ResponseEntity<ApiResponse> updateTask(@RequestBody ToDo toDoDetails, @PathVariable Integer id) {
        ToDo toDo = iTodoService.update(id, toDoDetails);
        return ResponseEntity.ok(new ApiResponse(true, "ToDo Updated Successfully", toDo));
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable("id") Integer id) {
        iTodoService.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "ToDO Delete Successfully"));
    }
}
