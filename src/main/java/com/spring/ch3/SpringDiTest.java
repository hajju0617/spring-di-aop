package com.spring.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;

//@Component("engine")
//class Engine {}
//@Component
//class SuperEngine extends Engine {}
//@Component
//class TurboEngine extends Engine {}
//@Component
//class Door {}
//@Component
//class Car {
//    @Value("red")
//    String color;
//    @Value("100")
//    int oil;
////    @Autowired
////    @Qualifier("superEngine")
//    @Resource(name = "superEngine22222")
//    Engine engine;
//
//    @Autowired
//    Door[] doors;
//    public Car() {
//
//    }
//
//    public Car(String color, int oil, Engine engine, Door[] doors) {
//        this.color = color;
//        this.oil = oil;
//        this.engine = engine;
//        this.doors = doors;
//    }
//
//    public void setColor(String color) {
//        this.color = color;
//    }
//
//    public void setOil(int oil) {
//        this.oil = oil;
//    }
//
//    public void setEngine(Engine engine) {
//        this.engine = engine;
//    }
//
//    public void setDoors(Door[] doors) {
//        this.doors = doors;
//    }
//    @Override
//    public String toString() {
//        return "Car{" +
//                "color='" + color + '\'' +
//                ", oil=" + oil +
//                ", engine=" + engine +
//                ", doors=" + Arrays.toString(doors) +
//                '}';
//    }
//}
//
//public class SpringDiTest {
//    public static void main(String[] args) {
//        ApplicationContext applicationContext = new GenericXmlApplicationContext("config2.xml");
//
////        Car car = (Car) applicationContext.getBean("car");          // by Name
//        Car car = applicationContext.getBean("car", Car.class);     // ()안에 Car.class로 타입을 적어줌으로써 형변환 안해도됨. (해당 코드는 바로 위 코드와 같은 코드임.)
////
////        Car car2 = (Car) applicationContext.getBean(Car.class);         // by Type
////        Engine engine = (Engine) applicationContext.getBean("engine");        // by Name
////        Engine superEngine = (Engine) applicationContext.getBean("superEngine");
////        Door door = (Door) applicationContext.getBean("door");
//
//
////        car.setColor("red");
////        car.setOil(100);
////        car.setEngine(engine);
////        // ↓ ()안에 타입을 적어주던가 명시적 형변환 해주던가. (getBean()의 반환타입이 Object이므로.)
////        car.setDoors(new Door[]{applicationContext.getBean("door", Door.class), (Door) applicationContext.getBean("door")});
//        System.out.println("car = " + car);
////        System.out.println("car2 = " + car2);
////        System.out.println("engine = " + engine);
////        System.out.println("door = " + door);
////        System.out.println("superEngine = " + superEngine);
//    }
//}
