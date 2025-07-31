package com.spring.ch3.diCopy2;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

class Car {}
class SportsCar extends Car {}
class Truck extends Car {}
class Engine {}
class AppContext {
    Map map;        // 객체들을 저장할 객체 저장소.

    AppContext() {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader("config.txt"));

            map = new HashMap(properties);      // Properties에 저장되어있는 내용들을 Mpa에 저장.

            for (Object key : map.keySet()) {   // 반복문으로 클래스 이름을 얻고 이걸 통해서 객체를 생성한 다음 Map에 저장.
                Class clazz = Class.forName((String) map.get(key));
                map.put(key, clazz.newInstance());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Object getBean(String key) {
        return map.get(key);        // Map의 key에 대한 value, 즉 객체를 반환해주는 메서드.
    }
}
public class Main2 {
    public static void main(String[] args) throws Exception {
        AppContext appContext = new AppContext();
        Car car = (Car) appContext.getBean("car");      // getBean 반환값이 Object이므로 (Car)로 형변환.
        System.out.println("car = " + car);
        Engine engine = (Engine) appContext.getBean("engine");
        System.out.println("engine = " + engine);
    }
}
