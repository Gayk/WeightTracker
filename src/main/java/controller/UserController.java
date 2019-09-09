package controller;

import model.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class UserController {
    private static String dataBaseName = "weighttrackdb";

    public static void main(String[] args){

        //todo
    }

    public static void userPanelforAdmin() {
        try (Connection conn = SQLController.getConnection(dataBaseName)) {
            Scanner scan = new Scanner(System.in);
            String option;
            while (true) {
                System.out.println("|Wybierz jedną z opcji:\n"
                        + "add - dodanie użytkownika\n"
                        + "edit - edycja użytkownika\n"
                        + "delete - usunięcie użytkownika\n"
                        + "quit - zakończenie programu");
                option = scan.next();
                switch (option) {
                    case "add":
                        add();
                        break;
                    case "edit":
                        edit();
                        break;
                    case "delete":
                        delete();
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
            System.out.println("nullpointer");
        }
    }
    public static void userPanelforUser() {
        try (Connection conn = SQLController.getConnection(dataBaseName)) {
            Scanner scan = new Scanner(System.in);
            String option;
            while (true) {
                System.out.println("|Wybierz jedną z opcji:\n"
                        + "1 - edytuj dane\n"
                        + "2 - dodaj wymiary\n"
                        + "3 - usuń wymiary z podanej daty\n"
                        + "q - zakończ działanie programu");
                option = scan.next();
                switch (option) {
                    case "1":
                        //todo
                        break;
                    case "2":
                        //todo
                        break;
                    case "3":
                        //todo
                        break;
                    case "1":
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
            System.out.println("nullpointer");
        }
    }

    private static void add() {
        try (Connection conn = SQLController.getConnection(dataBaseName)) {
            User newUser = new User();
            String login = ScannerController.nextString("Podaj login: ");
            String email = ScannerController.nextString("Podaj email: ");
            String password = ScannerController.nextString("Podaj hasło: ");
            newUser.setLogin(login);
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.saveToDB(conn);
            int id= newUser.getId();
            newUser = User.loadUserById(conn,id );
            if (newUser != null){
                System.out.println(newUser.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void edit() {
        User user = new User();
        try (Connection conn = SQLController.getConnection(dataBaseName)) {
            int userId = ScannerController.nextInt("Podaj id użytkownika: ");
            user = User.loadUserById(conn, userId);
            String login = "";
            String email = "";
            String password = "";
            String message = "Czy chcesz edytować te dane [t/n]?";
            do {
                    System.out.print("Nazwa użytkownika: \"" + user.getLogin() + "\"\n");
                if (ScannerController.yesNo(message)) {
                    login = ScannerController.nextString("Podaj nową nazwę użytkownika lub wpisz poprzednią: ");
                } else {
                        login = user.getLogin();
                }
                    System.out.print("Email: \"" + user.getEmail() + "\"\n");
                if (ScannerController.yesNo(message)) {
                    email = ScannerController.nextString("Podaj nowy adres email: ");
                } else {
                        email = user.getEmail();
                }
                    System.out.print("Hasło.");
                if (ScannerController.yesNo(message)) {
                    password = ScannerController.nextString("Podaj nowe hasło: ");
                } else {
                    System.out.print("Hasło nie zostało zmienione." + "\"\n");
                    }

                System.out.print(
                        String.format("Nowe dane: nazwa użytkownika: %s, email: %s", login,
                                email));
            } while (ScannerController.yesNo(". \nCzy chcesz ponownie edytować dane [t/n]?"));
                user.setLogin(login);
                user.setEmail(email);
                user.setPassword(password);
                user.saveToDB(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void delete() {
        String message = "Czy na pewno chcesz usunąć tego użytkownika [t/n]?";
        User user = new User();
        try (Connection conn = SQLController.getConnection(dataBaseName)) {
            int userId = ScannerController.nextInt("Podaj id użytkownika: ");
            user = User.loadUserById(conn, userId);
            if (user != null){
                user.toString();
            }
            if (ScannerController.yesNo(message)) {
                if (user != null) {
                    user.delete(conn);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
