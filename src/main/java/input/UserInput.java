package input;

import user.PrivilegeTypes;
import utils.SortOrder;

import java.util.*;

public class UserInput {
    private static final Scanner input = new Scanner(System.in);

    public static int mainMenu() {
        System.out.println("1. Inicijalizacija novog skladista.");
        System.out.println("2. Uloguj se.");
        System.out.println("3. Izloguj se.");
        System.out.println("4. Dodaj korisnika.");
        System.out.println("5. Prikaz fajlova.");
        System.out.println("6. Kreiranje direktorijuma i fajlova.");
        System.out.println("0. Izlaz.");
        return Integer.parseInt(input.nextLine());
    }

    public static int lsMenu() {
        System.out.println("1. Svi fajlovi.");
        System.out.println("2. Samo fajlovi, bez direktorijuma.");
        System.out.println("3. Samo direktorijumi.");
        System.out.println("4. Prikaz po tipu fajla.");
        System.out.println("5. Sortirano po imenu.");
        System.out.println("6. Sortirano po datumu.");
        System.out.println("7. Sortirano po vremenu poslednje modifikacije.");
        System.out.println("0. Nazad");
        return Integer.parseInt(input.nextLine());
    }

    public static String inputUsernameAndPassword() {
        System.out.println("Unesite korisnicko ime: ");
        String username = input.nextLine();
        System.out.println("Unesite lozinku: ");
        String password = input.nextLine();
        return username + ":" + password;
    }

    public static String inputStorageName() {
        System.out.println("Unesite ime skladista: ");
        return input.nextLine();
    }

    public static PrivilegeTypes inputPrivileges() {
        int selection;
        do {
            selection = privilegeMenu();
            if (selection == 1) {
                return PrivilegeTypes.ReadWrite;
            }
            if (selection == 2) {
                return PrivilegeTypes.ReadOnly;
            }
            if (selection == 3) {
                return PrivilegeTypes.None;
            }
        } while (true);
    }

    private static int privilegeMenu() {
        System.out.println("Unesite nivo privilegije; 1-ReadWrite, 2-ReadOnly, 3-None");
        return Integer.parseInt(input.nextLine());
    }

    public static String inputFileType() {
        System.out.println("Unesite tip fajla: ");
        return input.nextLine();
    }

    public static SortOrder inputSortOrder() {
        System.out.println("Unesite 'r' za rastuce ili 'o' za opadajuce");
        String sortOrder;
        do {
            sortOrder = input.nextLine();
            if (sortOrder.equals("r")) return SortOrder.ASC;
            if (sortOrder.equals("o")) return SortOrder.DESC;
        } while (true);
    }

    public static String inputPath() {
        System.out.println("Unesite putanju: ");
        return input.nextLine();
    }

    public static String inputPattern() {
        System.out.println("Zadajte sablon za kreiranje direktorijuma i fajlova. Unesite 'h' za pomoc ili 'n' da idete nazad.");
        String in;
        do {
            in = input.nextLine();
            if (in.equals("h")) {
                System.out.println("---------------------------------------------------------------------------");
                System.out.println("mkdir - kreiranje direktorijuma, mkfile - kreiranje fajlova");
                System.out.println("mkdir/mkfile ime1     - kreira fajl pod odredjenim imenom.");
                System.out.println("mkdir/mkfile ime1 ime2 ime3     - kreira vise fajlova pod odredjenim imenom.");
                System.out.println("mkdir/mkfile fajl{1..20}     - kreira 20 fajlova pod imenom fajl1,fajl2...fajl20");
                System.out.println("mkdir/mkfile random{n}     - kreira n fajlova sa imenom sastavljenim od 10 nasumicno izabranih karaktera.");
                System.out.println("---------------------------------------------------------------------------");
            } else if (in.equals("n")) break;
            else return in;
        } while (true);
        return "";
    }
}
