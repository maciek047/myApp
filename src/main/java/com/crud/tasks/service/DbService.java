package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DbService {

    @Autowired
    private TaskRepository repository;
    private Object Optional;
    private Object Task;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTaskById(final Long taskId) {
        return repository.findById(taskId).orElse(null);
    }
}

