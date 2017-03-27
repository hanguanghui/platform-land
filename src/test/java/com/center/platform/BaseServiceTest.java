package com.center.platform;


import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-config.xml", "classpath*:/spring-mvc.xml","classpath*:/spring-mybatis.xml"})
public class BaseServiceTest {

    public BaseServiceTest() {

    }

    protected void print(Object value) {
        System.out.print(value);
        System.out.println("\n");
    }

}
