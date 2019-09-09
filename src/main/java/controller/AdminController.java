package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class AdminController {
    private static String dataBaseName = "weighttrackdb";

    public static void main(String[] args) throws SQLException {
        System.out.println("Witamy w panelu Administratora!");

    }

    public static void adminMenu() throws SQLException {
        while (true) {
            String dataBaseName = "weighttrackdb";
            try (Connection conn = SQLController.getConnection(dataBaseName)) {
                Scanner scan = new Scanner(System.in);
                String option;
                while (true) {
                    System.out.println(
                            "|Wybierz jedną z opcji:\n"
                                    + "|  1 - przejdź do zarządzania użytkownikami\n"
                                    + "|  2 - przejdź do zarządzania wymiarami\n"
                                    + "|  q - zakończ");
                    option = scan.next();
                    switch (option) {
                        case "1":
                            UserController.userPanelforAdmin();
                            break;
                        case "2":
                            MeasurementsController.measurementsAdminPanel();
                            break;
                        case "q":
                            System.out.println("Program zakończył działanie.");
                            System.exit(0);
                        default:
                            System.out.print("Nie wpisałeś poprawnej odpowiedzi. ");
                            break;
                    }


                }
            }
        }
    }
}