package root.utils;

import java.util.ArrayList;

/**
 * Created by andrew on 10/28/15.
 */
public class CompareHelper { //all seems ok

    public static ArrayList<String> getMissed(ArrayList<String> first, ArrayList<String> toCheck){
        ArrayList<String> out = new ArrayList<>();
        for (String string : first) {
            if (!toCheck.contains(string)) {
                out.add(string);
            }
        }
        return out;
    }

    public static ArrayList<String> getAdditional(ArrayList<String> first, ArrayList<String> toCheck){
        ArrayList<String> out = new ArrayList<>();
        for (String string : toCheck) {
            if (!first.contains(string)) {
                out.add(string);
            }
        }
        return out;
    }

}
