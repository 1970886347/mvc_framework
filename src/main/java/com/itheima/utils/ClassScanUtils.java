package com.itheima.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ClassScanUtils {
    static List<Class>clazz=new ArrayList<Class>();
    static boolean recursion=true;
    public static List<Class>scanClasses(String pack) throws IOException, ClassNotFoundException {
        String packagePath = pack.replace(".", File.separator);
        Enumeration<URL> resources = ClassScanUtils.class.getClassLoader().getResources(packagePath);
        while (resources.hasMoreElements()){
            URL url = resources.nextElement();
            String protocol = url.getProtocol();
            if(protocol.equals("file")){
                String filePath = URLDecoder.decode(url.getFile(), "utf-8");
                getClasses(pack,filePath,recursion,clazz);
            }
        }
        return clazz;
    }

    private static void getClasses(String pack, String filePath, final boolean recursion, List<Class> clazz) throws ClassNotFoundException {
        File dir=new File(filePath);
        if(!dir.exists()||!dir.isDirectory()){
            return;
        }
        File[] files = dir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                boolean beDir = recursion && pathname.isDirectory();
                boolean beFile = pathname.isFile();
                return beDir||beFile;
            }
        });
        for (File file : files) {
            if(file.isDirectory()){
                getClasses(pack+"."+file.getName(),file.getAbsolutePath(),recursion,clazz);
            }else if(file.isFile()){
                Class cls=ClassScanUtils.class.getClassLoader().loadClass(pack+"."+file.getName().substring(0,file.getName().length()-6));
                clazz.add(cls);
            }
        }
    }
}
