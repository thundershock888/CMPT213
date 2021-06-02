package com.company;

class Task {
    String name;
    String notes;
    String date;
    int taskNum;
    boolean completed;

    public Task(String name, String notes, String date, int taskNum) {

        this.name = name;
        this.notes = notes;
        this.date = date;
        this.taskNum = taskNum;
        this.completed = false;
    }



    public void printSelf() {
        System.out.println("Task #" + taskNum);
        System.out.println("Task: " + name);
        System.out.println("Notes: " + notes);
        System.out.println("Date: " + date);
        System.out.println("Completed: " + completed);
    }

    public int getTaskNum() {
        return taskNum;
    }
    public void complteteTask(){
        completed = true;
    }

    public long getDateAsLong() {
        return Long.parseLong(date.replaceAll("[^\\d.]", ""));
    }
}
