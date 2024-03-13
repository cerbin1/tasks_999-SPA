package task.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import task.manager.entity.TaskFile;
import task.manager.entity.repository.TaskFilesRepository;

import java.io.IOException;
import java.util.Objects;

@Service
public class TaskFilesService {

    private final TaskFilesRepository filesRepository;

    @Autowired
    public TaskFilesService(TaskFilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    public TaskFile store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        TaskFile TaskFile = new TaskFile(fileName, file.getContentType(), file.getBytes());

        return filesRepository.save(TaskFile);
    }

    public TaskFile getFile(String id) {
        return filesRepository.findById(id).orElseThrow();
    }
}
