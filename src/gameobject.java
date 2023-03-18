import static java.lang.Integer.parseInt;
public class gameobject {
    int objectformation[][];
    int creationerror = 0;
    public gameobject(String size, String formation) {
        String infox = (size.split(","))[0];
        String infoy = (size.split(","))[1];
        int sizex = 0;
        int sizey = 0;
        String formationarray[];
        if (size.split(",").length != 2) {
            creationerror = 3;
        }
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
