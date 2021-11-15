package input;

import fs.StorageConfiguration;
import user.Credentials;
import user.PrivilegeTypes;
import user.Privileges;
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
        System.out.println("7. Brisanje direktorijuma i fajlova.");
        System.out.println("8. Preuzimanje fajlova.");
        System.out.println("9. Premestanje fajlova.");
        System.out.println("10. Konfiguracija skladista.");
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

    public static Credentials inputCredentials() {
        System.out.println("Unesite korisnicko ime: ");
        String username = input.nextLine();
        System.out.println("Unesite lozinku: ");
        String password = input.nextLine();
        return new Credentials(username, password);
    }

    public static Privileges inputPrivileges() {
        int selection;
        do {
            selection = privilegeMenu();
            if (selection == 1) {
                Privileges privileges = new Privileges(PrivilegeTypes.ReadWrite);
                boolean canDownload = inputCanDownload();
                privileges.setDownload(canDownload);
                return privileges;
            }
            if (selection == 2) {
                Privileges privileges = new Privileges(PrivilegeTypes.ReadOnly);
                boolean canDownload = inputCanDownload();
                privileges.setDownload(canDownload);
                return privileges;
            }
            if (selection == 3) {
                return new Privileges(PrivilegeTypes.None);
            }
        } while (true);
    }

    private static int privilegeMenu() {
        System.out.println("Unesite nivo privilegije; 1-ReadWrite, 2-ReadOnly, 3-None:");
        return Integer.parseInt(input.nextLine());
    }

    private static boolean inputCanDownload() {
        String canDownload;
        do {
            System.out.println("Da li zelite da omogucite preuzimanje fajlova ovom korisniku? (da/ne)");
            canDownload = input.nextLine();
            if (canDownload.equals("da")) return true;
            if (canDownload.equals("ne")) return false;
        } while (true);
    }

    public static String inputFileType() {
        System.out.println("Unesite tip fajla: ");
        return input.nextLine();
    }

    public static SortOrder inputSortOrder() {
        System.out.println("Unesite 'r' za rastuce ili 'o' za opadajuce:");
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

    public static String inputSourcePath() {
        System.out.println("Unesite izvornu putanju: ");
        return input.nextLine();
    }

    public static String inputDestinationPath() {
        System.out.println("Unesite odredisnu putanju: ");
        return input.nextLine();
    }

    public static String inputPattern() {
        System.out.println("Zadajte sablon za kreiranje direktorijuma i fajlova. Unesite 'h' za pomoc.");
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
            } else return in;
        } while (true);
    }

    public static StorageConfiguration inputConfiguration() {
        StorageConfiguration storageConfiguration = new StorageConfiguration();
        String selection;
        do {
            System.out.println("1. Konfiguracija maksimalne velicine skladista.");
            System.out.println("2. Konfiguracija nedozvoljenih fajl ekstenzija.");
            System.out.println("3. Konfiguracija maksimalnog broja fajlova po folderu.");
            System.out.println("0. Nazad");
            selection = input.nextLine();
            if (selection.equals("1")) inputMaxStorageSize(storageConfiguration);
            if (selection.equals("2")) inputRestrictedTypes(storageConfiguration);
            if (selection.equals("3")) inputFileCountRestrictions(storageConfiguration);
        } while (!selection.equalsIgnoreCase("0"));
        return storageConfiguration;
    }

    private static void inputMaxStorageSize(StorageConfiguration storageConfiguration) {
        System.out.println("Unesite maksimalnu velicinu skladista (u bajtovima):");
        storageConfiguration.setMaxSize(Long.parseLong(input.nextLine()));
    }

    private static void inputRestrictedTypes(StorageConfiguration storageConfiguration) {
        String ext;
        do {
            System.out.println("Unesite ekstenziju. Unesite 0 da idete nazad:");
            ext = input.nextLine();
            if (!ext.equals("0")) storageConfiguration.addRestrictedType(ext);
        } while (!ext.equals("0"));
    }

    private static void inputFileCountRestrictions(StorageConfiguration storageConfiguration) {
        String folder;
        int count;
        do {
            System.out.println("Unesite ime foldera. Unesite 0 da idete nazad:");
            folder = input.nextLine();
            if (folder.equals("0")) break;
            System.out.println("Unesite broj fajlova:");
            count = Integer.parseInt(input.nextLine());
            storageConfiguration.addFileCountRestriction(folder, count);
        } while (true);
    }
}
