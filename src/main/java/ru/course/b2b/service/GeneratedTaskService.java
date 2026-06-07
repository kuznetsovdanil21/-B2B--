package ru.course.b2b.service;

import javafx.collections.ObservableList;
import ru.course.b2b.data.GeneratedTaskRepository;
import ru.course.b2b.model.GeneratedTask;

public class GeneratedTaskService {

    public ObservableList<GeneratedTask> getTasks() {
        return GeneratedTaskRepository.getTasks();
    }

    public void addTask(
            GeneratedTask task
    ) {
        GeneratedTaskRepository.addTask(
                task
        );
    }
}