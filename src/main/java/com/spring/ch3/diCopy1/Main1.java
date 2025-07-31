package com.spring.ch3.diCopy1;

import java.io.FileReader;
import java.util.Properties;

class Car {}
class SportsCar extends Car {}
class Truck extends Car {}
class Engine {}
public class Main1 {
    public static void main(String[] args) throws Exception {
        Car car = (Car) getObject("car");      // getObject 반환값이 Object이므로 (Car)로 형변환.
        System.out.println("car = " + car);
        Engine engine = (Engine) getObject("engine");
        System.out.println("engine = " + engine);
    }
    static Object getObject(String key) throws Exception {      // String key : 어떤 타입의 객체를 받아올지.
        Properties properties = new Properties();
        properties.load(new FileReader("config.txt"));

        Class clazz = Class.forName(properties.getProperty(key));   // key를 이용해서 key에 대한 클래스의 정보를 얻음.
        return clazz.newInstance();                                 // 클래스의 정보를 이용해서 객체 생성 반환.
        // Object를 반환하므로 Car로 형변환.
    }

    static Car getCar() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileReader("config.txt"));

        Class clazz = Class.forName(properties.getProperty("car"));
        return (Car) clazz.newInstance();
        // Object를 반환하므로 (Car)로 형변환.
    }
}
