package com.company;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
public class Menu {
    Scanner scanner = new Scanner(System.in);
    ArrayList<Task> listOfTasks = new ArrayList<Task>();
    String[] menuOptions;

    public Menu(String[] menuOptions, ArrayList<Task> listOfTasks) {
        this.menuOptions = menuOptions;
        this.listOfTasks = listOfTasks;
    }

    public Menu() {

    }


    public void draw() {
        drawMenu(Constants.title);
        doTask(getUserInput(1, 7, true, "Choose an option by entering 1-7: "));
    }

    private void drawMenu(String title) {
        int title_length = title.length();
        String titleHeader = "";
        for (int i = 0; i < title_length + 2; i++) {
            titleHeader = titleHeader + "#";
        }
        System.out.println(titleHeader + "\n" + "#" + title + "#" + "\n" + titleHeader);
        int sizeofarray = menuOptions.length;
        for (int i = 0; i < sizeofarray; i++) {
            System.out.println((i + 1) + ": " + menuOptions[i]);
        }
        if (listOfTasks.size() == 0) {
            System.out.println("No tasks to show.");
        }
    }

    private String getUserInput(int lower, int upper, boolean numeric, String userQueury) {
        System.out.println(userQueury);
        String userInput = scanner.nextLine();
        if (numeric) {
            if (checkValidInput(lower, upper, userInput)) {
                return userInput;
            } else {
                return getUserInput(lower, upper, numeric, userQueury);
            }
        }
        return userInput;

    }

    private boolean checkValidInput(int lower, int upper, String userInput) {

        if (userInput != null && userInput.matches("[0-9.]+")) {
            int selectedNum = Integer.parseInt(userInput);
            if (selectedNum < lower || selectedNum > upper) {
                System.out.println("User input is not a valid option, please input a number " + lower + "-" + upper + ": ");
                return false;
            } else {
                return true;
            }

        } else {
            System.out.println("User input is not a valid option, please input a number " + lower + "-" + upper + ": ");
            return false;
        }
    }

    private void doTask(String input) {
        int todoTask = Integer.parseInt(input);
        if (todoTask == 1) {
            printTasks("num", listOfTasks);
        }
        if (todoTask == 2) {
            addNewTask();
        }
        if (todoTask == 3) {
            removeTask();
        }
        if (todoTask == 4) {
            markAsDone();
        }
        if (todoTask == 5) {
            showOverDue();
        }
        if (todoTask == 6) {
            showUpcoming();
        }
        if (todoTask == 7) {
            saveToJson();
        }
    }

    private void printTasks(String compareWith, ArrayList<Task> arr) {
        if (compareWith.equals("date")) {
            for (Task task : arr
            ) {
                //System.out.println("task.getdate() = " + task.getDateAsLong());
            }
            arr.sort(Comparator.comparing(Task::getDateAsLong));
        }
        if (compareWith.equals("num")) {

            arr.sort(Comparator.comparing(Task::getTaskNum));
        }
        System.out.println("Listing all tasks: ");
        if (arr.size() == 0) {
            System.out.println("No tasks to show");
        }
        for (Task task : arr) {
            task.printSelf();

        }
    }

    private void addNewTask() {
        int tasknum = findLargestTaskNum(listOfTasks) + 1;
        String taskname = getUserInput(0, 0, false, "Enter the name of the new task: ");
        String taskNotes = getUserInput(0, 0, false, "Enter the notes of the new task: ");
        String taskYear = getUserInput(0, 9999, true, "Enter the year of due date: ");
        String taskMonth = getUserInput(1, 12, true, "Enter the month of due date: ");
        String taskDay = getUserInput(1, 31, true, "Enter the day of due date: ");
        String taskHour = getUserInput(0, 24, true, "Enter the hour of due date: ");
        String taskMinute = getUserInput(0, 60, true, "Enter the minute of due date: ");
        taskHour = adjustDigitCount(2, taskHour);
        taskMinute = adjustDigitCount(2, taskMinute);
        taskMonth = adjustDigitCount(2, taskMonth);
        taskDay = adjustDigitCount(2, taskDay);
        taskYear = adjustDigitCount(4, taskYear);

        String newDate = taskYear + "-" + taskMonth + "-" + taskDay + " " + taskHour + ":" + taskMinute;
        Task newTask = new Task(taskname, taskNotes, newDate, tasknum);
        listOfTasks.add(newTask);
    }

    public static int findLargestTaskNum(ArrayList<Task> arr) {
        int largest = 0;
        for (Task task : arr
        ) {
            if (task.getTaskNum() > largest) {
                largest = task.getTaskNum();
            }
        }
        return largest;
    }

    public static String adjustDigitCount(int num, String s) {
        StringBuilder sb = new StringBuilder(s);
        if (s.length() < num) {
            int diff = num - s.length();

            for (int i = 0; i < diff; i++) {
                sb.insert(0, '0');
            }
        }

        return sb.toString();
    }

    private void removeTask() {

        printTasks("date", listOfTasks);
        String removeTask = getUserInput(0, findLargestTaskNum(listOfTasks), true, "Enter the number of the task you want to remove, or zero to cancel: ");
        int removeTaskNum = Integer.parseInt(removeTask);
        if (removeTaskNum == 0) {
            System.out.println("Cancelling removing task");
        } else {
            removeTaskNum = findIndex(removeTaskNum);
            if (removeTaskNum == -1) {
                System.out.println("Invalid number selected, cancelling removeTask. ");
            } else {
                System.out.println(listOfTasks.get(removeTaskNum).name + "Has Been Deleted");
                listOfTasks.remove(removeTaskNum);
            }
        }
    }

    private int findIndex(int taskNumber) {
        for (int i = 0; i < listOfTasks.size(); i++) {
            if (listOfTasks.get(i).taskNum == taskNumber) {
                return i;
            }
        }
        return -1;
    }

    private void markAsDone() {
        ArrayList<Task> incompletedTasks = listIncompletedTasks();
        String removeTask = getUserInput(0, findLargestTaskNum(incompletedTasks), true, "Enter the number of the task you want to mark as complete, or zero to cancel: ");
        int index = Integer.parseInt(removeTask);
        if (index == 0) {
            System.out.println("Cancelling operation to mark as done. ");
        } else {
            index = findIndex(index);
            if (index == -1) {
                System.out.println("selected task does not exist, Cancelling operation to mark as done. ");
            }
            System.out.println(listOfTasks.get(index).name + " Has Been Completed");
            listOfTasks.get(index).complteteTask();
        }
    }

    private ArrayList<Task> listIncompletedTasks() {
        System.out.println("Listing incomplete tasks: ");
        ArrayList<Task> incompletedTasks = new ArrayList<Task>();
        for (Task task : listOfTasks) {
            if (task.completed == false) {
                incompletedTasks.add(task);
            }
        }
        printTasks("date", incompletedTasks);
        return incompletedTasks;
    }

    private void showOverDue() {

        System.out.println("Printing OverDue Tasks: ");
        ArrayList<Task> overDueTasks = new ArrayList<Task>();
        for (Task task : listOfTasks) {
            long time = task.getDateAsLong();
            if (time < currentTimeasLong() && !task.completed) {
                overDueTasks.add(task);
            }
        }
        printTasks("date", overDueTasks);
    }

    private void showUpcoming() {
        System.out.println("Printing Upcoming Tasks: ");
        ArrayList<Task> upcomingTasks = new ArrayList<Task>();
        for (Task task : listOfTasks) {
            long time = task.getDateAsLong();
            if (time > currentTimeasLong() && !task.completed) {
                upcomingTasks.add(task);
            }
        }
        printTasks("date", upcomingTasks);
    }

    private long currentTimeasLong() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime currentTime = LocalDateTime.now();
        String timeString = dtf.format(currentTime);
        return removeNonDigitCharacters(timeString);
    }

    private long removeNonDigitCharacters(String s) {
        return Long.parseLong(s.replaceAll("[^\\d.]", ""));
    }

    private void saveToJson() {

        try {
            FileWriter myWriter = new FileWriter("Tasks.json");
            Gson gson = new Gson();
            for (Task task : listOfTasks
            ) {
                String json = gson.toJson(task);
                myWriter.write(json);
                myWriter.write("\n");
                System.out.println(json);
            }

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Main.runningState = 0;
    }
}
