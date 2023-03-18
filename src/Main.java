public class Main {
    /**
     * @param args
     *
     * Le main creer l'objet du jeux et si il n'a pas d'erreur continue dans le jeu, sinon, il quitte.
     *
     */
    public static void main(String[] args) {

        gamelist test = new gamelist();
        if (test.errorcheck == false) {
            test.playgame();
        }
    }
}