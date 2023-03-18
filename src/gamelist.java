
import java.io.File;
import java.util.Scanner;
public class gamelist {
    gamelevel[] levellist = new gamelevel[10];
    boolean errorcheck = false;
    boolean exitbool = false;
    public gamelist() {
        String filename = "";
        int levelpointer = 0;
        filename = "niveaux/niveau" + (levelpointer + 1) + ".txt";
        while (new File(filename).exists() == true && levelpointer < 10 && errorcheck == false) {
            levellist[levelpointer] = new gamelevel(levelpointer + 1);
            if (levellist[levelpointer].errortype != 0) {
                errorcheck = true;
            }
            filename = "niveaux/niveau" + (levelpointer + 1) + ".txt";
            levelpointer += 1;
        }
        if (new File(filename).exists() == false){
            errorcheck = true;
            System.out.println("Il manque un niveau, assurez-vous qu'il y en a 10");
        } else if (errorcheck != true) {
            System.out.println("Importation des fichiers complet");
        }
    }
    public void playgame(){
        int currentlevelpointer = 0;
        Scanner sc = new Scanner(System.in);
        String input;

        System.out.println("                     _                                 \n" +
                "  /\\/\\   ___  _ __ | |_ ___ _____   _ _ __ ___   __ _ \n" +
                " /    \\ / _ \\| '_ \\| __/ _ \\_  / | | | '_ ` _ \\ / _` |\n" +
                "/ /\\/\\ \\ (_) | | | | ||  __// /| |_| | | | | | | (_| |\n" +
                "\\/    \\/\\___/|_| |_|\\__\\___/___|\\__,_|_| |_| |_|\\__,_|\n" +
                "                                                      \n" +
                "\n"+
                "\n"+
                "Olivier Saint-Vincent" +
                "\n");
        System.out.println("Appuyez sur entrer pour continuer");
        input = sc.nextLine();
        while (currentlevelpointer < 10 && exitbool == false){
            if (currentlevelpointer > 0) {
                System.out.println("Allons au prochain niveau");
            }
            levellist[currentlevelpointer].playlevel();
            exitbool = levellist[currentlevelpointer].exit;
            currentlevelpointer +=1;
        }
        if (exitbool == true) {
            System.out.println("Au revoir");
        } else {
            System.out.println("Bravo, vous avez fini le jeu!");
        }
    }
}

