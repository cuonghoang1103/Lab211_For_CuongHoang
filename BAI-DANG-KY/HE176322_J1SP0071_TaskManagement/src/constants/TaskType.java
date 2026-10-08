/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package constants;

/**
 *
 * @author pc
 */
//enum dinh nghia cac loai task
public enum TaskType {
    CODE(1, "Code"),
    TEST(2, "Test"),
    DESIGN(3, "Design"),
    REVIEW(4, "Review");
    private int id;
    private String name;
//contructor cua enum

    private TaskType(int id, String name) {
        this.id = id;
        this.name = name;
    }
//chuy doi id sang tasktype tuong ung   

    public static TaskType fromId(int id) {
        for (TaskType t : values()) {
            if (t.id == id) {
                return t;

            }
        }
        return null;
    }
//lay ten cua task der hien thi ra view

    public String getName() {
        return name;
    }

}
