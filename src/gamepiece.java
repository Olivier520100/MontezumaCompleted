import java.util.Arrays;

import static java.lang.Integer.parseInt;

/**
 * gamepiece (piece du jeu) est une extension de la classe gameobjet (objet du jeu)
 * qui ajoute une variable used (utiliser) qui indique si la piece etait deja utiliser.
 * Il ajoute aussi un tableau coordinates, qui indique la position de la piece si elle dans le jeu
 */
public class gamepiece extends gameobject{
    boolean used;
    int coordinates[]= new int[2];
    public gamepiece(String size, String formation) {
        super(size, formation);
    }
}
