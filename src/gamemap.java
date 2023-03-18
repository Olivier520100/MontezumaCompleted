public class gamemap extends gameobject{

    /**
     * @param size meme que dans gameobject
     * @param formation meme que dans gameobject
     */
    public gamemap(String size, String formation) {
        /**
         * cela change toute les valeurs de 1 a 100
         * Pourquoi?
         * Quand on affiche la carte, ca va etre beaucoup plus facile de distinguer quelles sont les murs au non des pieces.
         * Ca ne le fait pas si il avait une erreur avant.
         */
        super(size, formation);
        int i = 0;
        int j = 0;
        if (creationerror == 0) {
            while (i < objectformation.length) {
                while (j < objectformation[0].length) {
                    if (objectformation[i][j] == 1) {
                        objectformation[i][j] = 100;
                    }
                    j += 1;
                }
                j = 0;
                i += 1;
            }
        }

    }
}
