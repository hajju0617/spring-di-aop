package com.spring.ch3.aop;

import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AopMain {
    public static void main(String[] args) throws Exception {
        MyAdvice myAdvice = new MyAdvice();

        // MyClass 객체를 동적으로 생성해서 MyAdvice의 invoke() 메서드로 넘겨주기.
        Class myClass = Class.forName("com.spring.ch3.aop.MyClass");        // MyClass의 클래스 객체 얻어오기.
        Object object = myClass.newInstance();                                       // 클래스 객체로 부터 객체 생성하기.

        // 반복문을 이용해서 MyClass에 있는 메서드들을 하나씩 호출하기.
        for (Method method : myClass.getDeclaredMethods()) {
            // myClass.getDeclaredMethods() : myClass에 정의 되어 있는 메서드들을 배열로 갖고 와서 하나씩 꺼면서 반복문 수행.

            myAdvice.invoke(method, object, null);  // myAdvice의 invoke() 메서드로 myClass에 정의 되어 있는 메서드들이 하나씩 호출됨.
        }
    }
}

class MyAdvice {
    // 정규식을 이용해서 메서드 이름이 a로 시작하는 것만.
    Pattern pattern = Pattern.compile("a.*");


    boolean matches(Method method) {
        Matcher matcher = pattern.matcher(method.getName());
        return matcher.matches();
    }
    void invoke(Method method, Object object, Object...args) throws Exception{
        if (method.getAnnotation(Transactional.class) != null) {
            System.out.println("[before] {");
        }
        method.invoke(object, args);            // aaa(), bbb(), ccc() 호출 가능.
        if (method.getAnnotation(Transactional.class) != null) {
            System.out.println("} [after]");
        }
    }
}

class MyClass {
    void aaa() {
        System.out.println("aaa is called");
    }
    @Transactional
    void bbb() {
        System.out.println("bbb is called");
    }
    void ccc() {
        System.out.println("ccc is called");
    }
}
