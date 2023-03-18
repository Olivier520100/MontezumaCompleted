import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class gamelevel {
    /**
     * levelnumber indique c'est quelle niveau
     * exit est un boolean qui indique si le joueur a fait !
     * map est la carte de jeu
     * piecelist est la liste des pieces de jeu
     * errortype indique si il y a eu une erreur si la valeur n'est pas 0
     * levelcompleted indique si le niveau est completer
     */
    int levelnumber;
    boolean exit = false;
    gamemap map;
    gamepiece piecelist[];
    int errortype = 0;
    boolean levelcompleted = false;

    /**
     * @param number est le numero du niveau et est connue et verifier car on a verifier que le fichier existe dans gamelist
     *
     */
    public gamelevel(int number) {
        /**
         *
         * Parcours le fichier et creer les objets de carte et pieces
         * Il indique si il y a eu une erreur dans errortype
         * le try assure que le est au cas ou que le fichier n'existe pas, meme si c'est impossible si il est rendu ici
         */
        try {
            levelnumber = number;
            String fileName = "niveaux/niveau"+ levelnumber+".txt";
            BufferedReader filebuffer = new BufferedReader(new FileReader(fileName));
            int linecount = 0;
            while ((filebuffer.readLine()) != null) {
                linecount += 1;
            }
            String levelstringarray[] = new String[linecount];
            BufferedReader filebufferreset = new BufferedReader(new FileReader(fileName));
            int currentline = 0;
            while (currentline < linecount) {
                levelstringarray[currentline] = (filebufferreset.readLine());
                currentline += 1;
            }
            piecelist = new gamepiece[levelstringarray.length - 1];
            int i = 0;
            boolean errorcheck = false;
            int gcount = 0;
            int pcount = 0;
            while (i < levelstringarray.length && errorcheck == false) {
                String gameobjectsplit[] = levelstringarray[i].split("\\|");
                if (gameobjectsplit.length != 3) {
                    errorcheck = true;
                } else {
                    if (gameobjectsplit[0].equals("G")) {
                        gcount += 1;
                        if (gcount > 1) {
                            errortype = 1;
                            errorcheck = true;
                        } else {
                            map = new gamemap(gameobjectsplit[1],gameobjectsplit[2]);
                            if (map.creationerror != 0){
                                errorcheck = true;
                                errortype = 3+piecelist[pcount-1].creationerror;
                            }
                        }
                    } else if (gameobjectsplit[0].equals("P")) {
                        pcount += 1;
                        if (pcount > levelstringarray.length - 1) {
                            errorcheck = true;
                            errortype = 2;
                        } else {
                            piecelist[pcount-1] = new gamepiece(gameobjectsplit[1],gameobjectsplit[2]);
                            if (piecelist[pcount-1].creationerror != 0){
                                errorcheck = true;
                                errortype = 3+piecelist[pcount-1].creationerror;
                            }
                        }
                    } else {
                        errortype = 3;
                        errorcheck = true;
                    }
                }
                i += 1;
            }
            if (gcount == 0){
                errorcheck=true;
            }
            if (errorcheck) {
                System.out.println("Erreur dans le fichier du niveau\n" + "Veuillez verifier votre fichier");
                map = null;
                piecelist = null;
            }
        } catch (IOException e){
            System.out.println("Fichier non-existant");
        }
    }

    /**
     * @param piecenumber numero du piece dans la liste
     * @param xcoord coordonee x dans la carte
     * @param ycoord coordonee y dans la carte
     */
    public void addpiecevaldation(int piecenumber, int xcoord, int ycoord){

        /**
         * verifie que la piece peut etre mis dans l'endroit indiquer par le joueur, et si oui, le fait.
         *
         */
        int xcoordpointer = xcoord;
        int ycoordpointer = ycoord;
        int piecexcoord = 0;
        int pieceycoord = 0;
        boolean overlap = false;

        if (((ycoord + piecelist[piecenumber].objectformation.length) > map.objectformation.length) || ((xcoord + piecelist[piecenumber].objectformation[0].length) > map.objectformation[0].length)) {
            overlap = true;
        }
        while ((ycoordpointer < (ycoord + piecelist[piecenumber].objectformation.length)) && overlap == false) {
            while ((xcoordpointer < (xcoord + piecelist[piecenumber].objectformation[0].length)) && overlap == false) {

                if (map.objectformation[ycoordpointer][xcoordpointer] != 0 && piecelist[piecenumber].objectformation[pieceycoord][piecexcoord] != 0) {
                    overlap = true;
                }
                xcoordpointer += 1;
                piecexcoord += 1;
            }
            xcoordpointer = xcoord;
            piecexcoord = 0;
            ycoordpointer += 1;
            pieceycoord += 1;
        }
        if (overlap == true) {
            if (piecelist[piecenumber].used == true) {
                addpiecevaldation(piecenumber, piecelist[piecenumber].coordinates[0], piecelist[piecenumber].coordinates[1]);
            }
            System.out.println("Impossible de placer a cet endroit");
        } else {
            piecelist[piecenumber].used = true;
            piecelist[piecenumber].coordinates[0] = xcoord;
            piecelist[piecenumber].coordinates[1] = ycoord;
            placepiece(piecenumber, xcoord, ycoord);
        }
    }

    /**
     * @param piecenumber numero de piece dans la liste
     * @param xcoord coordonee x dans la carte
     * @param ycoord coordonee y dans la carte
     */
    public void placepiece(int piecenumber, int xcoord, int ycoord) {
        /**
         * Change les valeur dans la formation de l'objectformation dans l'objet de la carte selon le numero de l'objet.
         */
        int piecexcoord = 0;
        int pieceycoord = 0;
        while (pieceycoord < piecelist[piecenumber].objectformation.length){
            while (piecexcoord < piecelist[piecenumber].objectformation[0].length) {
                if (piecelist[piecenumber].objectformation[pieceycoord][piecexcoord] == 1) {
                    map.objectformation[pieceycoord+ycoord][piecexcoord+xcoord] = piecenumber+1;
                }
                piecexcoord += 1;
            }
            pieceycoord+=1;
            piecexcoord=0;
        }
    }

    /**
     * @param piecenumber numero de la piece dans la liste
     */
    public void removepiece(int piecenumber) {

        /**
         * Enleve la piece si il est sur la carte.
         */
        if (piecelist[piecenumber].used == true) {
            int xcoord = piecelist[piecenumber].coordinates[0];
            int ycoord = piecelist[piecenumber].coordinates[1];
            int piecexcoord = 0;
            int pieceycoord = 0;
            while (pieceycoord < piecelist[piecenumber].objectformation.length) {
                while (piecexcoord < piecelist[piecenumber].objectformation[0].length) {
                    if (piecelist[piecenumber].objectformation[pieceycoord][piecexcoord] == 1) {
                        map.objectformation[pieceycoord + ycoord][piecexcoord + xcoord] = 0;
                    }
                    piecexcoord += 1;
                }
                pieceycoord += 1;
                piecexcoord = 0;
            }
            piecelist[piecenumber].used = false;
        } else {
            System.out.println("Piece pas utiliser");
        }
    }

    /**
     * @param piecenumber numero de la piece
     * @param xcoord coordonnee x
     * @param ycoord coordonee y
     */
    public void movepiece(int piecenumber, int xcoord, int ycoord){
        /**
         * Si la piece est deja utiliser, ca l'enleve, et le place de nouveau, si c'est impossible c'a le replace
         * Dans le else, ca fait juste essayer de placer
         */
        if (piecelist[piecenumber].used == true) {
            removepiece(piecenumber);
            piecelist[piecenumber].used = true;
            addpiecevaldation(piecenumber, xcoord, ycoord);

        } else {

            addpiecevaldation(piecenumber, xcoord, ycoord);
        }
    }

    /**
     * Montre le niveau
     */
     public void displaylevel(){
         String charliststring = "█ABCDEFGHIJKLMNOPQRSTUVWXYZ░";
         String displayarray[] = new String [map.objectformation.length+3];
         String []charlist = charliststring.split("");
         int piecexcoord = 0;
         int pieceycoord = 0;
         int charpointer = 1;
         int i = 0;
         int layerpointer = 0;
         displayarray[0] = "   ";
         while (i < map.objectformation[0].length){
             charpointer = i + 1;
             displayarray[0]+=charlist[charpointer];
             i+=1;
         }
         i = 0;
         i = 0;
         displayarray[1] = "  █";
         while (i < map.objectformation[1].length){
             charpointer = i + 1;
             displayarray[1]+=charlist[0];
             i+=1;
         }
         displayarray[1]+=charlist[0];
         displayarray[displayarray.length-1] = displayarray[1];
         charpointer = 0;
         i=0;
         while (layerpointer < map.objectformation.length) {
             displayarray[layerpointer+2] = (layerpointer + 1) + " █";
             while (i < map.objectformation[layerpointer].length) {
                 if (map.objectformation[layerpointer][i] == 100) {
                     charpointer = 0;
                 } else {
                     charpointer = 27 - map.objectformation[layerpointer][i];
                 }
                 displayarray[layerpointer+2] += charlist[charpointer];
                 i += 1;
             }
             displayarray[layerpointer+2]+=charlist[0];
             i = 0;
             layerpointer+=1;
         }
         layerpointer = 0;
         displayarray[layerpointer] += "  Pieces restantes:";
         layerpointer+=2;
         while (i < piecelist.length) {
             if (piecelist[i].used == false) {
                 while (pieceycoord < piecelist[i].objectformation.length) {
                     displayarray[layerpointer + pieceycoord] += "   ";
                     while (piecexcoord < piecelist[i].objectformation[0].length) {
                         charpointer = 27 - (i + 1) * (piecelist[i].objectformation[pieceycoord][piecexcoord]);
                         displayarray[layerpointer + pieceycoord] += charlist[charpointer];
                         piecexcoord += 1;
                     }
                     piecexcoord = 0;
                     pieceycoord += 1;
                 }
             }
             while (pieceycoord < map.objectformation.length) {
                 displayarray[layerpointer + pieceycoord] += "   ";
                 for (int j = 0; j < piecelist[i].objectformation[0].length; j++ ){
                     displayarray[layerpointer + pieceycoord] += " ";
                 }
                 pieceycoord+=1;
             }
             i += 1;
             pieceycoord = 0;
         }
         i=0;
         System.out.println("--- Montezuma+ --- N" + levelnumber);
         while (i < displayarray.length) {
             System.out.println(displayarray[i]);
             i+=1;
         }
         System.out.println("! pour quitter >>" );
     }

    /**
     * Capabilite d'entrer les commandes
     * @param commandin la commande
     */
     public void command(String commandin) {
         String charliststring = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
         String commandincharlist[] = commandin.split("");
         int char1num;
         int char2num;
         int char3num;
         if (commandincharlist[0].equals("<")) {
             runscript(commandin.substring(1, commandin.length()));
         } else if (commandin.length() == 1) {
             if (commandincharlist[0].equals("!")) {
                exit = true;
             } else {
                 char1num = 25 - (charliststring).indexOf(commandincharlist[0].toUpperCase());
                 if (char1num > piecelist.length || char1num == -1) {
                     System.out.println("Piece Invalid");
                 } else {
                     removepiece(char1num);
                 }
             }
         } else if (commandin.length() == 3) {
             char1num =  25 - charliststring.indexOf(commandincharlist[0].toUpperCase());
             if (char1num > piecelist.length){
                 System.out.println("Piece Invalid");
             } else {
                 try {
                 char2num =  (charliststring).indexOf(commandincharlist[1].toUpperCase());
                 char3num =  Integer.parseInt(commandincharlist[2]) - 1;
                 if (char3num >= 0 && char2num >= 0){
                     movepiece(char1num,char2num,char3num);
                 }} catch (NumberFormatException e){
                     System.out.println("Commande Invalid");
                 }
             }
         } else {
             System.out.println("Commande Invalid");
         }
     }

    /**
     * Verifie si le niveau est fini
     */
    public void levelcompletecheck() {
        levelcompleted = true;
        int i = 0;
        while (i < piecelist.length){
            if (piecelist[i].used == false) {
                levelcompleted= false;

            }
            i+=1;
        }
    }
    /**
     * Joue le niveau
     */
    public void playlevel() {
        String inputstr;
        Scanner sc = new Scanner(System.in);
        displaylevel();
        while (levelcompleted == false && exit == false) {
            inputstr = sc.next();
            command(inputstr);
            if (exit == false) {
                levelcompletecheck();
                displaylevel();
            }
        }
    }

    /**
     * permet de utiliser fichier de commandes
     * @param fileName fichier de commande
     */
    public void runscript(String fileName){
        String currentline;
        try {
            BufferedReader filebuffer = new BufferedReader(new FileReader(fileName));
            while ((currentline = filebuffer.readLine()) != null) {
                if ((currentline.split("")[0].equals("!") == false) && (currentline.split("")[0].equals("<") == false)) {
                    command(currentline);
                }
            }
            filebuffer.close();
        } catch (IOException e) {
            System.out.println("Ce script n'existe pas");
        }
    }
}
