package root.utils;

import root.CLI;
import root.MyConfig;
import root.view.Preconfigurator;

import java.io.*;
import java.net.URISyntaxException;
import java.security.CodeSource;

public class ConfigCarer {

    public static MyConfig giveMeMyFuckingConfig(){
        MyConfig config = ConfigCarer.loadConf();
        if (!Preconfigurator.lazyCheck(config)) {
            Preconfigurator.preconfigure(config);
            try {
                ConfigCarer.saveConf(config);
            } catch (UnsupportedEncodingException e) {
                CLI.printIntoConsole("Exception while saving config");
                CLI.refreshMainStageWithExplanation(e, true, "Saving config");
            }
        }
        return config;
    }

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
            if (!(new File(jarDir + "/" + "config.out").exists())){
                return new MyConfig(null, null, null, null, null);
            }
            System.out.println("config exist");
            FileInputStream fis = new FileInputStream(jarDir + "/" + "config.out");
            ObjectInputStream oin = new ObjectInputStream(fis);
            return (MyConfig)oin.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
