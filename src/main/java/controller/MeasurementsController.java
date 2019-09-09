package controller;

import model.Measurements;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class MeasurementsController {
    private static String dataBaseName = "weighttrackdb";

    public static void main(String[] args){
        measurementsPanel();
    }

    public static void measurementsPanel() {
        while (true) {
            try (Connection conn = SQLController.getConnection(dataBaseName)) {
                Scanner scan = new Scanner(System.in);
                String option;

                System.out.println("|Wybierz jedną z opcji:\n"
                        + "add - dodaj nowe wymiary\n"
                        + "view - przeglądanie wymiarów użytkownika\n"
                        + "delete - usuwanie wymiarów z danego dnia\n"
                        + "quit - zakończenie programu");
                option = scan.next();
                switch (option) {
                    case "add":
                        add();
                        break;
                        //dobudowac jeszce edit
                    case "view":
                        view();
                        break;
                    case "delete":
                        delete();
                        break;
                    case "quit":
                        System.out.println("Program zakończył działanie.");
                        System.exit(0);
                    default:
                        System.out.print("Nie wpisałeś poprawnej odpowiedzi. \n");
                        break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("Brak wymiarów w bazie.");
            }
        }
    }


    private static void add() {
        try (Connection conn = SQLController.getConnection(dataBaseName)) {
            int userId = ScannerController.nextInt("Podaj numer użytkownika: ");
            String reg_date = ScannerController.nextString("Podaj datę: ");
            int height = ScannerController.nextInt("Podaj wzrost: ");
            int weight = ScannerController.nextInt("Podaj wagę: ");
            int arms = ScannerController.nextInt("Podaj obwód ramion: ");
            int chest = ScannerController.nextInt("Podaj obwód klatki piersiowej: ");
            int waist = ScannerController.nextInt("Podaj obwód talii: ");
            int hips = ScannerController.nextInt("Podaj obwód bioder: ");
            Measurements newMeasurements = new Measurements();
            newMeasurements.setUserId(userId);
            newMeasurements.setRegistrationDate(reg_date);
            newMeasurements.setHeight(height);
            newMeasurements.setWeight(weight);
            newMeasurements.setArms(arms);
            newMeasurements.setChest(chest);
            newMeasurements.setWaist(waist);
            newMeasurements.setHips(hips);
            newMeasurements.saveToDB(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void delete() {
        try (Connection conn = SQLController.getConnection(dataBaseName)) {
            System.out.println(Arrays.toString(Measurements.loadAllMeasurements(conn)));
            String reg_date = ScannerController.nextString("Podaj datę: ");
            Measurements newMeasurements = new Measurements();
            newMeasurements.deleteByDate(conn, reg_date);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void view() {
        try (Connection conn = SQLController.getConnection(dataBaseName)) {
            int userId = ScannerController.nextInt("Podaj numer użytkownika: ");
            Measurements.loadAllByUserId(conn, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
