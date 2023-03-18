
import java.io.File;
import java.util.Scanner;
public class gamelist {
    /**
     * List d'objet de niveau
     */
    gamelevel[] levellist = new gamelevel[10];
    /**
     * Boolean qui indique si il y a une erreur
     */
    boolean errorcheck = false;
    /**
     * Boolean qui indique si il a eu une erreur lors du chargement
     */
    boolean exitbool = false;

    /**
     * Va a travers les fichiers
     * Si il manque un fichier, le jeu s'arrete et demande de mettre les 10 niveaux
     * Verifie si il y a eu un erreur dans les niveaux en regardant errortype
     * Quitte le while si un fichier manque ou il y a eu une erreur dans le chargement
     * Si il n'y a pas d'erreur, il inscrit "Importation complet" et permet de continuer au jeu.
     */
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

    /**
     * Debut du jeu
     * Page intro et apres commence le jeux en utilisant la methode .playlevel de la classe niveau.
     * Quitte si le joueur a voulu quiter en regardant le boolean exit boolean.
     */
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

