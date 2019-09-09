import controller.MeasurementsController;
import controller.SQLController;
import controller.ScannerController;
import controller.UserController;
import model.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/*TODO
1.panel -> "logowanie" -> haslo i email -> walidacja do bazy danych(nowa metoda?):
 a)jesli poprawne dane to na sztywno ustawia się id usera(user_id)
 b)jesli niepoprawne dane to sout "niepoprawne dane. czy chcesz spróbowac jeszce raz? t/n" lu od razu zakonczyc program(ewnetualnie np
 po 3. probach :))
 c) admin logowanie np na uzytkownika admin admin123 ;)

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
