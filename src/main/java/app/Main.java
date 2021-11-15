package app;

import dnl.utils.text.table.TextTable;
import exceptions.FileStorageException;
import fs.FileStorage;
import fs.MyFile;
import fs.StorageConfiguration;
import input.UserInput;
import user.Credentials;
import user.Privileges;
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
        initialStorage(args[0]);
        int selection = 0;
        do {
            try {
                selection = UserInput.mainMenu();
                if (selection == 1) {
                    String storagePath = UserInput.inputPath();
                    Credentials credentials = UserInput.inputCredentials();
                    fs.init(storagePath, credentials);
                    System.out.printf("Uspesno ste inicijalizovali novo skladiste '%s'! Trenutni korisnik: %s.\n", storagePath, credentials);
                }
                if (selection == 2) {
                    String storagePath = UserInput.inputPath();
                    Credentials credentials = UserInput.inputCredentials();
                    fs.login(storagePath, credentials);
                    System.out.printf("Uspesno ste se ulogovali u skladiste '%s'! Trenutni korisnik: %s.\n", storagePath, credentials.toString());
                }
                if (selection == 3) {
                    fs.logout();
                    System.out.println("Uspesno ste se izlogovali.");
                }
                if (selection == 4) {
                    Credentials credentials = UserInput.inputCredentials();
                    Privileges privileges = UserInput.inputPrivileges();
                    fs.addUser(credentials, privileges);
                    System.out.printf("Uspesno ste dodali novog %s korisnika.\n", privileges.getType().toString());
                }
                if (selection == 5) {
                    int ls;
                    String path;
                    do {
                        ls = UserInput.lsMenu();
                        if (ls == 0) break;
                        path = UserInput.inputPath();
                        if (ls == 1) print_ls(fs.lsAll(path));
                        if (ls == 2) print_ls(fs.lsFiles(path));
                        if (ls == 3) print_ls(fs.lsDirectories(path));
                        if (ls == 4) print_ls(fs.lsByType(path, UserInput.inputFileType()));
                        if (ls == 5) print_ls(fs.lsSortedByName(path, UserInput.inputSortOrder()));
                        if (ls == 6) print_ls(fs.lsSortedByDate(path, UserInput.inputSortOrder()));
                        if (ls == 7) print_ls(fs.lsSortedByLastModified(path, UserInput.inputSortOrder()));
                    } while (true);
                }
                if (selection == 6) {
                    fs.makeFiles(UserInput.inputPattern(), UserInput.inputPath());
                }
                if (selection == 7) {
                    String path = UserInput.inputPath();
                    fs.deleteFile(path);
                    System.out.printf("Uspesno ste obrisali fajl %s.\n", path);
                }
                if (selection == 8) {
                    String path = UserInput.inputPath();
                    fs.downloadFile(path);
                    System.out.printf("Uspesno ste preuzeli fajl %s u Downloads folder!\n", path);
                }
                if (selection == 9) {
                    String source = UserInput.inputSourcePath();
                    String destination = UserInput.inputDestinationPath();
                    fs.moveFile(source, destination);
                    System.out.printf("Uspesno ste premestili %s u %s\n", source, destination);
                }
                if (selection == 10) {
                    StorageConfiguration storageConfiguration = UserInput.inputConfiguration();
                    fs.configureStorage(storageConfiguration);
                    System.out.println("Uspesno ste konfigurisali skladiste! " + storageConfiguration.toString());
                }
            } catch (FileStorageException | NumberFormatException exception) {
                System.out.println(exception.getMessage());
            }
        } while (selection != 0);
    }

    private static void initialStorage(String name) {
        FileStorage fs = StorageManager.getStorage();
        System.out.printf("Inicijalizacija novog skladista '%s'\n", name);
        Credentials credentials = UserInput.inputCredentials();
        fs.init(name, credentials);
        System.out.printf("Trenutno skladiste: '%s'. Trenutni korisnik: %s.\n", name, credentials.getUsername());
    }

    private static void print_ls(List<MyFile> files) {
        TextTable tt = new TextTable(new String[]{"Name", "Created time", "Modified time", "Extension"}, toPrimitiveArray(files));
        tt.printTable();
        System.out.println("----------------------------------------------------------------------------");
    }

    private static Object[][] toPrimitiveArray(List<MyFile> files) {
        Object[][] out = new Object[files.size()][4];
        for (int i = 0; i < files.size(); i++) {
            out[i][0] = files.get(i).getFileName();
            out[i][1] = files.get(i).getDate();
            out[i][2] = files.get(i).getLastModified();
            out[i][3] = files.get(i).getType();
        }
        return out;
    }
} // -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005