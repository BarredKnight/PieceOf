package root.utils;

import root.MyConfig;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.security.CodeSource;

/**
 * Created by andrew on 11/1/15.
 */
public class ConfigCarer {

    public static boolean saveConf(MyConfig config) throws UnsupportedEncodingException {
        CodeSource codeSource = ConfigCarer.class.getProtectionDomain().getCodeSource();
        File jarFile = null;
        try {
            jarFile = new File(codeSource.getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String jarDir = jarFile.getParentFile().getPath();
        try {
            FileOutputStream fos = new FileOutputStream(jarDir + "/config.out");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(config);
            oos.flush();
            oos.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return true;
    }

    public static MyConfig loadConf(){
        CodeSource codeSource = ConfigCarer.class.getProtectionDomain().getCodeSource();
        File jarFile = null;
        try {
            jarFile = new File(codeSource.getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String jarDir = jarFile.getParentFile().getPath();
        try {
            if (!(new File(jarDir + "temp.oup").exists())){
                return new MyConfig(null, null, null, null, null);
            }
            FileInputStream fis = new FileInputStream(jarDir + "temp.out");
            ObjectInputStream oin = new ObjectInputStream(fis);
            return (MyConfig)oin.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
