/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import constants.Message;
import controller.TaskController;
import dto.TaskRequestDTO;
import java.util.Date;
import java.util.Scanner;
import utils.Validation;

/**
 *
 * @author pc
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskController taskController = new TaskController();
//vong lap hien thi MENU        
        while (true) {
            System.out.println(Message.MENU);
            System.out.println(Message.INPUT_CHOICE);
            try {
//Doc va kiem tra lua lua chon cua nguoi dungtrong khoang tu 1-4                 
                int choice = Validation.getChoice(sc.nextLine(), 1, 4);
                switch (choice) {
                    case 1:
                        //them task
                        TaskRequestDTO dto = new TaskRequestDTO();
                        //nhap requirement
                        String requirementName = null;
                        while (requirementName == null) {
                            System.out.print(Message.INPUT__REQUIREMENT);
                            try {
                                requirementName = Validation.getString(sc.nextLine());

                            } catch (Exception e) {
                                System.out.println(e.getMessage());

                            }
                        }
                        dto.setRequirementName(requirementName);
                        Integer taskTypeId = null;
                        while (taskTypeId == null) {
                            //nhap task type
                            System.out.print(Message.INPUT_TASK_TYPE);
                            try {
                                taskTypeId = Validation.validateTaskType(sc.nextLine());
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        dto.setTaskTypeID(taskTypeId);
                        Date date = null;
                        while (date == null) {
                            //nhap Date
                            System.out.print(Message.INPUT_DATE);
                            try {
                                String dateStr = Validation.getString(sc.nextLine());
                                date = Validation.validateDate(dateStr);

                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        dto.setDate(date);
                        double from = 0,
                         to = 0;
                        boolean validTime = false;
                        while (!validTime) {
                            try {
                                //nhap thoi gian bat dau
                                System.out.print(Message.INPUT_FROM);
                                from = Validation.getPositiveDouble(sc.nextLine());
                                //nhap thoi gian ket thuc
                                System.out.print(Message.INPUT_TO);
                                to = Validation.getPositiveDouble(sc.nextLine());

                                Validation.validatePlantime(from, to);
                                validTime = true;

                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        dto.setFrom(from);

                        dto.setTo(to);

                        String assignee = null;
                        while (assignee == null) {
                            //Nhap nguoi duoc giao viec
                            System.out.print(Message.INPUT_ASSIGNEE);
                            try {
                                assignee = Validation.getString(sc.nextLine());

                            } catch (Exception e) {
                                System.out.println(e.getMessage());

                            }
                        }
                        dto.setAssignee(assignee);

                        String reviewer = null;
                        while (reviewer == null) {
                            //nhap nguoi giao viec
                            System.out.print(Message.INPUT_REVIEWER);
                            try {
                                reviewer = Validation.getString(sc.nextLine());

                            } catch (Exception e) {
                                System.out.println(e.getMessage());

                            }
                        }
                        dto.setReviewer(reviewer);
                        taskController.addTask(dto);
                        break;

                    case 2:
                        //xoa task
                        System.out.print(Message.INPUT_ID);
                        int id = Validation.getPositiveInt(sc.nextLine());
                        taskController.deleteTask(id);
                        break;

                    case 3:
                        //hien thi task
                        taskController.displayTask();
                        break;

                    case 4:
                        //ket thuc chuong trinh
                        return;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
