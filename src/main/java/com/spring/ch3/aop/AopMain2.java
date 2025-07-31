package com.spring.ch3.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class AopMain2 {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new GenericXmlApplicationContext(
        "file:src/main/webapp/WEB-INF/spring/**/root-context_aop.xml");

        MyMath myMath = (MyMath) applicationContext.getBean("myMath");   // getBean()으로 MyMath 가져오기.
//        System.out.println("myMath.add(3, 5) = " + myMath.add(3, 5));
//        System.out.println("myMath.multiply(3, 5) = " + myMath.multiply(3, 5));
        myMath.add(3, 5);
        myMath.add(1, 2, 3);
        System.out.println("myMath.multiply(3, 5) = " + myMath.multiply(3, 5));
    }
}
