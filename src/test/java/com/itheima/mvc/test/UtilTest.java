package com.itheima.mvc.test;

import com.itheima.utils.ClassScanUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class UtilTest {
    @Test
    public void scanClassesTest() throws IOException, ClassNotFoundException {
        List<Class> classes = ClassScanUtils.scanClasses("com.itheima.controller");
        System.out.println(classes);
    }
}
