import controller.MeasurementsController;
import controller.SQLController;
import controller.ScannerController;
import controller.UserController;
import model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

class Homepage {
    private  UserController userController;
    private static MeasurementsController measurementsController;

    public static void main(String[] args) {
            while (true) {
                String dataBaseName = "weighttrackdb";
                try (Connection conn = SQLController.getConnection(dataBaseName)) {
                    User user = new User();
                    Scanner scan = new Scanner(System.in);
                    String option;
                    while (true) {
                        System.out.println(
                                "|Wybierz jedną z opcji:\n"
                                        + "|  panel - przejdź do panelu użytkownika\n"
                                        + "|  wymiary - przejdź do zarządzania wymiarami\n"
                                        + "|  quit - zakończ");
                        option = scan.next();
                        switch (option) {
                            case "panel":
                                UserController.userPanel();
                                break;
                                case "wymiary":
                                    MeasurementsController.measurementsPanel();
                                break;
                                case "quit":
                                    System.out.println("Program zakończył działanie.");
                                    System.exit(0);
                                default:
                                    System.out.print("Nie wpisałeś poprawnej odpowiedzi. ");
                                    break;
                            }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    System.out.println("Niepoprawne dane");
                }
            }
        }
    }
