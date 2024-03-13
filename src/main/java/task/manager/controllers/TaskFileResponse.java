package task.manager.controllers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TaskFileResponse {
    private String name;
    private String url;
    private String type;
    private long size;
}
