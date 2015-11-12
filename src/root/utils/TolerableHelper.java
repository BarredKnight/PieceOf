package root.utils;

import root.MyConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrew on 11/4/15.
 */
public class TolerableHelper {
    public static String alphabetEngAndNumb = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static ArrayList<String> getPathsOfLocalRepos(MyConfig config){
        ArrayList<String> allPaths = new ArrayList<>();
        File[] rootContent = new File(config.wayToRepos).listFiles();
        for (File file : rootContent){
            if (file.isDirectory()){
                allPaths.add(file.getAbsolutePath());
            }
        }
        return allPaths;
    }

    public static ArrayList<File> getLocalRepos(MyConfig config){
        ArrayList<File> allRepos = new ArrayList<>();
        File[] rootContent = new File(config.wayToRepos).listFiles();
        if (rootContent != null)
        for (File file : rootContent){
            if (file.isDirectory()){
                allRepos.add(file);
            }
        }
        return allRepos;
    }

    public static ArrayList<String> getNamesOfLocalRepos(MyConfig config){
        ArrayList<String> allPaths = getPathsOfLocalRepos(config);
        ArrayList<String> namesOfLocalRepos = new ArrayList<>();
        for (String path : allPaths){
            File repo = new File(path);
            namesOfLocalRepos.add(repo.getName());
        }
        return namesOfLocalRepos;
    }

    public static ArrayList<String> getNamesOfHubRepos(MyConfig config){
        return null;
    }

    public static String transliterate(String source){
        final Map<Character, String> charMap = new HashMap<Character, String>();

                charMap.put('А', "A");
                charMap.put('Б', "B");
                charMap.put('В', "V");
                charMap.put('Г', "G");
                charMap.put('Д', "D");
                charMap.put('Е', "E");
                charMap.put('Ё', "E");
                charMap.put('Ж', "Zh");
                charMap.put('З', "Z");
                charMap.put('И', "I");
                charMap.put('Й', "I");
                charMap.put('К', "K");
                charMap.put('Л', "L");
                charMap.put('М', "M");
                charMap.put('Н', "N");
                charMap.put('О', "O");
                charMap.put('П', "P");
                charMap.put('Р', "R");
                charMap.put('С', "S");
                charMap.put('Т', "T");
                charMap.put('У', "U");
                charMap.put('Ф', "F");
                charMap.put('Х', "H");
                charMap.put('Ц', "C");
                charMap.put('Ч', "Ch");
                charMap.put('Ш', "Sh");
                charMap.put('Щ', "Sh");
                charMap.put('Ъ', "'");
                charMap.put('Ы', "Y");
                charMap.put('Ь', "'");
                charMap.put('Э', "E");
                charMap.put('Ю', "U");
                charMap.put('Я', "Ya");
                charMap.put('а', "a");
                charMap.put('б', "b");
                charMap.put('в', "v");
                charMap.put('г', "g");
                charMap.put('д', "d");
                charMap.put('е', "e");
                charMap.put('ё', "e");
                charMap.put('ж', "zh");
                charMap.put('з', "z");
                charMap.put('и', "i");
                charMap.put('й', "i");
                charMap.put('к', "k");
                charMap.put('л', "l");
                charMap.put('м', "m");
                charMap.put('н', "n");
                charMap.put('о', "o");
                charMap.put('п', "p");
                charMap.put('р', "r");
                charMap.put('с', "s");
                charMap.put('т', "t");
                charMap.put('у', "u");
                charMap.put('ф', "f");
                charMap.put('х', "h");
                charMap.put('ц', "c");
                charMap.put('ч', "ch");
                charMap.put('ш', "sh");
                charMap.put('щ', "sh");
                charMap.put('ъ', "'");
                charMap.put('ы', "y");
                charMap.put('ь', "'");
                charMap.put('э', "e");
                charMap.put('ю', "u");
                charMap.put('я', "ya");

                StringBuilder transliteratedString = new StringBuilder();
                for (int i = 0; i < source.length(); i++) {
                    Character ch = source.charAt(i);
                    String charFromMap = charMap.get(ch);
                    if (charFromMap == null) {
                        transliteratedString.append(ch);
                    } else {
                        transliteratedString.append(charFromMap);
                    }
                }
                return transliteratedString.toString();
            }   // foreign code

    public static String convertToCorrectName(String source){
        ArrayList<Character> allowable = new ArrayList<>();

        allowable.add('.');
        allowable.add(',');
        allowable.add('-');
        allowable.add('№');
        allowable.add('/');

        String spaceless = substituteSpaces(source);
        String transliterated = transliterate(spaceless);
        String out = "";
        for (Character ch : transliterated.toCharArray()){
            if (allowable.contains(ch) || alphabetEngAndNumb.contains(String.valueOf(ch)))
                out = out + ch;
        }
        return out;
    }

    public static String substituteSpaces(String source){
        String out = "";
        for (char ch : source.toCharArray()){
            if (ch == ' ')
                out = out + "-";
            else
                out = out + ch;
        }
        return out;
    }

    public static ArrayList<String> getRight(ArrayList<String> notRight){
        ArrayList<String> out = new ArrayList<>();
        for (String notRPath : notRight){
            String right = convertToCorrectName(substituteSpaces(notRPath));
            out.add(right);
        }
        return out;
    }

    public static String getRight(String notRight){
        return convertToCorrectName(substituteSpaces(notRight));
    }

}
