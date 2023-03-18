public class gamemap extends gameobject{
    String currentmap[][];
    public gamemap(String size, String formation) {
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
