import static java.lang.Integer.parseInt;

/**
 * gameobject ou (objet du jeu) est un modele pour soit la carte de jeu ou une piece
 * comme dans le fichier, les deux on la meme forme, c'etait plus facile de creer un modele pour les deux aulieu d'avoir
 * eu deux objet avec les meme lignes de codes
 *
 */
public class gameobject {
    /**
     * objectformation indique la forme de l'objet, des 0 si vide, 1 si rempli
     */
    int objectformation[][];
    /**
     * creationerror est un boolean qui indique si il y a eu un erreur lors du chargement de l'objet
     */
    int creationerror = 0;

    /**
     * @param size est le string "(x),(y)"
     * @param formation est le string qui indique quelle sont rempli "100001101110"
     */
    public gameobject(String size, String formation) {

        /**
         *
         * infox est le string de la taille en x et la meme chose en y
         * c'est pas directement mis en parseint, parce que le ca va etre dans le try directement apres
         * le if verifie si les tailles sont bien ecrit
         *
         */
        String infox = (size.split(","))[0];
        String infoy = (size.split(","))[1];
        int sizex = 0;
        int sizey = 0;
        String formationarray[];
        if (size.split(",").length != 2) {
            creationerror = 3;
        }

        /**
         *
         * Ensuite il verifie si il y a eu une erreur avant avant de continuer
         * il parse les information en int dans un try et converti la formation en tableau 2d
         * il y a une section qui peut changer la valeur de craetionerror, c'est quand une des valeurs dans formation est
         * pas 0 ou 1. Exemple "20010000111"
         *
         * Il y aussi un erreur si ce n'est pas des chiffres.
         */
        if (creationerror == 0) {
            try {
                sizex = parseInt(infox);
                sizey = parseInt(infoy);
                if ((sizex * sizey) != formation.length()) {
                    creationerror = 4;
                }
                objectformation = new int[sizex][sizey];
                formationarray = formation.split("");
                int i = 0;
                int j = 0;
                int counter = 0;
                while (i < sizex && creationerror == 0) {
                    while (j < sizey && creationerror == 0) {

                        objectformation[i][j] = parseInt(formationarray[counter]);
                        if (objectformation[i][j] > 1 || objectformation[i][j] < 0) {
                            creationerror = 2;
                        }
                        counter += 1;
                        j += 1;
                    }
                    j = 0;
                    i += 1;
                }
            } catch (NumberFormatException e) {
                creationerror = 1;
            }
        }
    }
}
