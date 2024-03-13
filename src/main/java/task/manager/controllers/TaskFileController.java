package task.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import task.manager.entity.Task;
import task.manager.entity.TaskFile;
import task.manager.entity.repository.TasksRepository;
import task.manager.service.TaskFilesService;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class TaskFileController {

    private final TaskFilesService taskFilesService;
    private final TasksRepository tasksRepository;

    @Autowired
    public TaskFileController(TaskFilesService taskFilesService, TasksRepository tasksRepository) {
        this.taskFilesService = taskFilesService;
        this.tasksRepository = tasksRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFiles(@RequestParam("files") List<MultipartFile> filesToUpload, @RequestParam Long taskId) {
        String message;
        try {
            Task task = tasksRepository.findById(taskId).orElseThrow();
            for (MultipartFile multipartFile : filesToUpload) {
                TaskFile taskFile = taskFilesService.store(multipartFile);
                task.getTaskFiles().add(taskFile);
            }
            tasksRepository.save(task);
            message = "Uploaded files successfully: " + filesToUpload.stream().map(MultipartFile::getOriginalFilename);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the files: " + filesToUpload.stream().map(MultipartFile::getOriginalFilename);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        TaskFile taskFile = taskFilesService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + taskFile.getName() + "\"")
                .body(taskFile.getData());
    }
}
