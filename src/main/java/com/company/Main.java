package com.company;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    static int runningState = 1;
    static ArrayList<Task> listOfTasks = new ArrayList<Task>();
    static class LoadJson{
        public static void loadJson(){
            try {
                File myObj = new File("Tasks.json");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            try {
                File myObj = new File("Tasks.json");
                Scanner myReader = new Scanner(myObj);
                Gson gson = new Gson();
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    Task task = gson.fromJson(data, Task.class);
                    listOfTasks.add(task);
                    System.out.println(data);
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        LoadJson.loadJson();

        String[] menuArray = {"List all Tasks", "Add a new task", "Remove a task", "Mark a task as completed", "List overdue incomplete tasks", "List upcoming incomplete tasks","Exit"};

        listOfTasks = Tester.populateList(listOfTasks);
        Menu newMenu = new Menu(menuArray, Main.listOfTasks);
        while(Main.runningState == 1) {
            newMenu.draw();
        }

    }

}
