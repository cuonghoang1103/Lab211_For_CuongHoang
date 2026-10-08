package main;

import constants.Message;
import controller.WorkerController;
import dto.WorkerRequestDTO;
import java.util.Scanner;
import utils.Validation;

public class Main {

    // Lap lai cho den khi nhap chuoi hop le, khong thoat ve menu khi sai
    private static String promptString(Scanner sc, String message) {
        while (true) {
            try {
                System.out.print(message);
                return Validation.getString(sc.nextLine());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // Lap lai cho den khi nhap tuoi hop le
    private static int promptAge(Scanner sc, String message) {
        while (true) {
            try {
                System.out.print(message);
                return Validation.getAge(sc.nextLine());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // Lap lai cho den khi nhap so duong hop le
    private static double promptPositiveDouble(Scanner sc, String message) {
        while (true) {
            try {
                System.out.print(message);
                return Validation.getPositiveDouble(sc.nextLine());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        
        //Khai bao dau vao nhap lieu
        Scanner sc = new Scanner(System.in);
        
        //Khai bao controller
        WorkerController workerController = new WorkerController();

        // Tao vong den khi thoa man cac dieu kien ngoai le
        while (true) {
            System.out.println(Message.MENU);
            System.out.print(Message.ENTER_CHOICE);

            try {
                // Goi Validation check nhap so tu 1 den 5
                int choice = Validation.getChoice(sc.nextLine(), 1, 5);
                
                switch (choice) {
                    case 1:
                        //Khai bao Request
                        WorkerRequestDTO addDto = new WorkerRequestDTO();
                        System.out.println(Message.TITLE_ADD_WORKER);
                        //Nhap tung field, lap lai rieng tung field neu sai
                        addDto.setId(promptString(sc, Message.ENTER_CODE));
                        addDto.setName(promptString(sc, Message.ENTER_NAME));
                        addDto.setAge(promptAge(sc, Message.ENTER_AGE));
                        addDto.setSalary(promptPositiveDouble(sc, Message.ENTER_SALARY));
                        addDto.setLocation(promptString(sc, Message.ENTER_LOCATION));
                        
                        // Controller xu ly them moi
                        workerController.addWorker(addDto);
                        break;
                        
                    case 2:
                        System.out.println(Message.TITLE_SALARY);
                        String incCode = promptString(sc, Message.ENTER_CODE);
                        double incAmount = promptPositiveDouble(sc, Message.ENTER_SALARY);
                        
                        // Controller xu ly tang luong
                        workerController.increaseSalary(incCode, incAmount);
                        break;
                        
                    case 3:
                        System.out.println(Message.TITLE_SALARY);
                        String decCode = promptString(sc, Message.ENTER_CODE);
                        double decAmount = promptPositiveDouble(sc, Message.ENTER_SALARY);
                        
                        // Controller xu ly giam luong
                        workerController.decreaseSalary(decCode, decAmount);
                        break;
                        
                    case 4:
                        System.out.println(Message.TITLE_DISPLAY);
                        // Controller in ra man hinh
                        workerController.displaySalaryHistory();
                        break;
                        
                    case 5:
                        // Thoat chuong trinh
                        return;
                }
            } catch (Exception e) {
                // In loi bat ky 
                System.out.println(e.getMessage());
            }
        }
    }
}