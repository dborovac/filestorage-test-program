package input;

import user.PrivilegeTypes;

import java.util.Scanner;

public class UserInput {
    private static final Scanner input = new Scanner(System.in);

    public static String inputUsernameAndPassword() {
        System.out.println("Unesite korisnicko ime: ");
        String username = input.nextLine();
        System.out.println("Unesite lozinku: ");
        String password = input.nextLine();
        return username + ":" + password;
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
        int selection;
        System.out.println("Unesite nivo privilegije; 1-ReadWrite, 2-ReadOnly, 3-None");
        selection = input.nextInt();
        return selection;
    }
}
