package com.example; // This is required because of your folder structure

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ToDoList {
    // --- The Data Logic ---
    private List<String> tasks = new ArrayList<>();

    public void addTask(String task) {
        tasks.add(task);
    }

    public List<String> getTasks() {
        return tasks;
    }

    // --- The User Interface ---
    public void runInteractiveMode() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- To-Do List Application ---");
        
        while (true) {
            System.out.println("\n1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.print("Enter task description: ");
                String task = scanner.nextLine();
                addTask(task);
                System.out.println("Task added!");
            } else if (choice.equals("2")) {
                System.out.println("\n--- Your Tasks ---");
                List<String> currentTasks = getTasks();
                if (currentTasks.isEmpty()) {
                    System.out.println("No tasks found.");
                } else {
                    for (int i = 0; i < currentTasks.size(); i++) {
                        System.out.println((i + 1) + ". " + currentTasks.get(i));
                    }
                }
            } else if (choice.equals("3")) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void main(String[] args) {
        ToDoList app = new ToDoList();
        app.runInteractiveMode();
    }
}