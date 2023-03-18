import java.util.Arrays;

import static java.lang.Integer.parseInt;

public class gamepiece extends gameobject{
    boolean used;
    int coordinates[]= new int[2];
    public gamepiece(String size, String formation) {
        super(size, formation);
    }
}
