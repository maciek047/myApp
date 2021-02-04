package com.crud.tasks.controller;

import com.crud.tasks.domain.*;
import com.crud.tasks.facade.TrelloFacade;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskController taskController;

    @Test
    public void testGetTasks() throws Exception {
        //Given
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(new TaskDto(1L,"task1","task content1"));

        when(taskController.getTasks()).thenReturn(taskDtoList);
        // When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id",is(1)))
                .andExpect(jsonPath("$[0].title", is("task1")))
                .andExpect(jsonPath("$[0].content", is("task content1")));
    }

    @Test
    public void getTask() throws Exception {
        //Given
        TaskDto testedTask = new TaskDto(2L,"task2", "task content2");

        when(taskController.getTask(2L)).thenReturn(testedTask);
        // When & Then
        mockMvc.perform(get("/v1/task/getTask?taskId=2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(2)))
                .andExpect(jsonPath("$.title", is("task2")))
                .andExpect(jsonPath("$.content", is("task content2")));
    }

    @Test
    public void deleteTask() throws Exception {
        //Given
        doNothing().when(taskController).deleteTask(1L);
        // When & Then
        mockMvc.perform(delete("/v1/task/deleteTask?taskId=1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateTask() throws Exception {
        //Given
        TaskDto testedTask = new TaskDto(3L,"task3", "task content3");
        TaskDto updatedTask = new TaskDto(3L, "task3.1", "updated content");
        Gson gson = new Gson();
        String jsonContent = gson.toJson(testedTask);

        when(taskController.updateTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(updatedTask);
        // When & Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(3)))
                .andExpect(jsonPath("$.title", is("task3.1")))
                .andExpect(jsonPath("$.content", is("updated content")));
    }

    @Test
    public void createTask() throws Exception {
        //Given
        TaskDto createdTask = new TaskDto(4L,"task4", "task content4");
        Gson gson = new Gson();
        String jsonContent = gson.toJson(createdTask);

        doNothing().when(taskController).createTask(createdTask);
        // When & Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}