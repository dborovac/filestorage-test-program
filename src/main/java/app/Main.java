package app;

import fs.FileStorage;
import fs.MyFile;
import input.UserInput;
import user.User;
import utils.StorageManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            return;
        }
        try {
            Class.forName("storage.RemoteImpl");
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        FileStorage fs = StorageManager.getStorage();
        System.out.printf("Inicijalizacija novog skladista '%s'\n", args[0]);
        fs.init(args[0], UserInput.inputUsernameAndPassword());
        System.out.printf("Trenutno skladiste: '%s'\n", args[0]);
        int selection;
        do {
            selection = UserInput.mainMenu();
            if (selection == 1) {
                String storageName = UserInput.inputStorageName();
                fs.init(storageName, UserInput.inputUsernameAndPassword());
                System.out.println("Trenutno skladiste: '" + storageName + "'");
            }
            if (selection == 2) {
                String storageName = UserInput.inputStorageName();
                fs.login(storageName, UserInput.inputUsernameAndPassword());
                System.out.println("Trenutno skladiste: '" + storageName + "'");
            }
            if (selection == 3) {
                fs.logout();
                System.out.println("Uspesno ste se izlogovali.");
            }
            if (selection == 4) {
                fs.addUser(UserInput.inputUsernameAndPassword(), UserInput.inputPrivileges());
                System.out.println("Uspesno ste dodali novog korisnika.");
            }
            if (selection == 5) {
                int ls;
                do {
                    ls = UserInput.lsMenu();
                    if (ls == 1) print_ls(fs.ls());
                    if (ls == 2) print_ls(fs.lsFiles());
                    if (ls == 3) print_ls(fs.lsDirectories());
                    if (ls == 4) print_ls(fs.lsByType(UserInput.inputFileType()));
                    if (ls == 5) print_ls(fs.lsSortedByName(UserInput.inputSortOrder()));
                    if (ls == 6) print_ls(fs.lsSortedByDate(UserInput.inputSortOrder()));
                    if (ls == 7) print_ls(fs.lsSortedByLastModified(UserInput.inputSortOrder()));
                } while (ls != 0);
            }
            if (selection == 6) {
                fs.makeFiles(UserInput.inputPattern(), UserInput.inputPath());
            }
        } while (selection != 0);
    }

    private static void print_ls(List<MyFile> files) {
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Name \t\t\t Created time \t\t\t Modified time \t\t\t Extension");
        System.out.println("---------------------------------------------------------------------------");
        for (MyFile file : files) {
            System.out.println(file.getFileName() + "\t" + file.getDate() + "\t" + file.getLastModified() + "\t" + file.getType());
        }
        System.out.println("---------------------------------------------------------------------------");
    }
} // -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005