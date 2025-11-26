package com.example; // Required

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ToDoTest {
    private ToDoList app;

    @BeforeEach
    void setUp() {
        app = new ToDoList();
    }

    @Test
    void testAddTask() {
        app.addTask("Buy Milk");
        assertEquals(1, app.getTasks().size(), "Task list should have 1 item");
    }

    @Test
    void testMultipleTasks() {
        app.addTask("Task 1");
        app.addTask("Task 2");
        assertEquals(2, app.getTasks().size());
    }
}