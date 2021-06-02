package com.company;

import java.util.ArrayList;

public class Tester {
    public static ArrayList<Task> populateList(ArrayList<Task> lot){

        for (int i = 0; i < 50; i++) {
            String taskHour = Integer.toString((int)(Math.random()*24));
            String taskMinute = Integer.toString((int)(Math.random()*60));
            String taskMonth = Integer.toString((int)(Math.random()*12)+1);
            String taskDay = Integer.toString((int)(Math.random()*31)+1);
            String taskYear = Integer.toString((int)(Math.random()*9999));
            taskHour = Menu.adjustDigitCount(2,taskHour);
            taskMinute = Menu.adjustDigitCount(2,taskMinute);
            taskMonth = Menu.adjustDigitCount(2,taskMonth);
            taskDay = Menu.adjustDigitCount(2,taskDay);
            taskYear = Menu.adjustDigitCount(4,taskYear);

            String newDate = taskYear+ "-" + taskMonth + "-" + taskDay + " " + taskHour + ":" + taskMinute;
            int tasknum = Menu.findLargestTaskNum(lot)+1;
            Task t = new Task("Hi", "", newDate, tasknum);
            lot.add(t);
        }
        return lot;
    }
}