import controller.*;
import model.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/*TODO
1.panel -> "logowanie" -> haslo i email -> walidacja do bazy danych(nowa metoda?):
 a)jesli poprawne dane to na sztywno ustawia się id usera(user_id)
 b)jesli niepoprawne dane to sout "niepoprawne dane. czy chcesz spróbowac jeszce raz? t/n" lu od razu zakonczyc program(ewnetualnie np
 po 3. probach :))
 c) admin poprzedzone logowaniem np

 flow a: wtedy wyswietlamy panel usera, gdzie na sztywno do kazdej metody db wstawiamy user_id naszego usera zalogwanego:)):
  -> można wyswietlic swoje dane/wymiary(view) -> albo modyfikacja istniejacej metody, albo druga wersja
  -> mozna usunac swoje dane/wymiary(delete) -> -..-
  -> mozna edytowac swoje dane/wymiary(edit) -> -..-

[w tym celu proponuje zrobic rozlam klas na metody, ktore sa dedykowane adminowi i metody, ktore sa dedykowane userowi]

  flow c:
  jako admin mozna uzywac dotychczasowych metod -> czyli wybieracie po user_id czyje wymiary etc checie ogladac, usuwac

+dodatkowo mozna uporzadkowac kod, pochowac niektore rzeczy jesli da rade
+mozna zmienic menu na forme opcji:1,2,3 -> bedzie latwiej wpisywac :)
*/


class Homepage {
    private  UserController userController;
    private AdminController adminController;
    private static MeasurementsController measurementsController;

    public static void main(String[] args) {
            while (true) {
                String dataBaseName = "weighttrackdb";
                try (Connection conn = SQLController.getConnection(dataBaseName)) {
                    Scanner scan = new Scanner(System.in);
                    String option;
                    while (true) {
                        System.out.println(
                                "|Witaj w konsolowej aplikacji Weight Tracker!\n"
                                        + "|  Wybierz, w jaki sposób chcesz korzystać z naszego programu:\n"
                                        + "|  1 - zaloguj się do swojego konta\n"
                                        + "|  2 - zarządzaj bazą jako administrator\n"
                                        + "|  q - zakończ działanie programu");
                        option = scan.next();
                        switch (option) {
                            case "1":
                                //todo wywolac logowanie klienta
                                break;
                            case "2":
                                AdminController.adminMenu();
                                break;
                            case "q":
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
