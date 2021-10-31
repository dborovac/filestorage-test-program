package app;

import fs.FileStorage;
import input.UserInput;
import storage.RemoteImpl;
import user.PrivilegeTypes;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            return;
        }
        FileStorage fs = new RemoteImpl();
        int selection;
        do {
            selection = menu();
            if (selection == 1) {
                fs.init(args[0], UserInput.inputUsernameAndPassword());
            }
            if (selection == 2) {
                fs.addUser(UserInput.inputUsernameAndPassword(), UserInput.inputPrivileges());
            }
        } while (selection != 0);
    }

    public static int menu() {
        int selection;
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Inicijalizacija skladista.");
        System.out.println("2. Dodaj korisnika.");
        System.out.println("0. Izlaz.");
        selection = scanner.nextInt();
        return selection;
    }
} // -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005