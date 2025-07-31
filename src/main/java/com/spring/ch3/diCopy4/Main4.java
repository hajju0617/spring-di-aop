package com.spring.ch3.diCopy4;


import com.google.common.reflect.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
class Car {
    @Resource
    Engine engine;
//    @Resource
    Door door;

    @Override
    public String toString() {
        return "Car{" +
                "engine=" + engine +
                ", door=" + door +
                '}';
    }
}
@Component
class SportsCar extends Car {}
@Component
class Truck extends Car {}
@Component
class Door {}
@Component
class Engine {}

class AppContext {
    Map map;        // 객체들을 저장할 객체 저장소.

    AppContext() {
        map = new HashMap();
        doComponentScan();      // @Component 에노테이션이 붙은 클래스들을 객체로 생성해서 Map에 저장하는 메서드.
        doAutowired();          // @Autowired를 처리.
        doResource();
    }

    private void doResource() {
        // Map에 저장된 객체의 iv중에 @Resource가 붙어 있으면
        // Map에서 iv의 이름에 맞는 객체를 찾아서 연결해줌. (객체의 주소를 iv에 저장.)
        try {
            for (Object bean : map.values()) {
                for (Field field : bean.getClass().getDeclaredFields()) {
                    if (field.getAnnotation(Resource.class) != null) {      // by Name
                        field.set(bean, getBean(field.getName()));          // @Resource가 붙은 필드에 getBean으로 이름을 조회해서 value(주소값)을 가져와서 넣어줌.
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void doAutowired() {
        // Map에 저장된 객체의 iv중에 @Autowired가 붙어 있으면
        // Map에서 iv의 타입에 맞는 객체를 찾아서 연결해줌. (객체의 주소를 iv에 저장.)
        try {
            for (Object bean : map.values()) {
                for (Field field : bean.getClass().getDeclaredFields()) {
                    if (field.getAnnotation(Autowired.class) != null) {     // by Type
                        field.set(bean, getBean(field.getType()));          // @Autowired가 붙은 필드에 getBean으로 타입(value)을 가져와서 넣어줌.
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void doComponentScan() {
        try {
            // 1. 패키지 내의 클래스 목록을 가져옴.
            ClassLoader classLoader = AppContext.class.getClassLoader();
            ClassPath classPath = ClassPath.from(classLoader);
            Set<ClassPath.ClassInfo> classInfoSet = classPath.getTopLevelClasses("com.spring.ch3.diCopy4");
            // 2. 반복문으로 클래스를 하나씩 읽어와서 @Component 에노테이션이 붙어 있는지 확인.
            for (ClassPath.ClassInfo classInfo : classInfoSet) {
                Class clazz = classInfo.load();     // 클래스 정보 얻어오기.
                Component component = (Component) clazz.getAnnotation(Component.class);     // @Component 에노테이션이 붙어있는 지 확인.
                if (component != null) {                // 3. @Component 에노테이션이 붙어있다면 객체를 생성해서 Map에 저장.
                    String id = StringUtils.uncapitalize(classInfo.getSimpleName());
                    map.put(id, clazz.newInstance());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Object getBean(String key) {                // 이름으로 객체 찾기.
        return map.get(key);                    // Map의 key에 대한 value, 즉 객체를 반환해주는 메서드.
    }
    Object getBean(Class clazz) {               // 타입으로 객체 찾기.
        for (Object obj : map.values()) {
            if (clazz.isInstance(obj)) {        // obj가 매개변수로 받은 clazz의 객체이거나 자식이면 true.
                return obj;
            }
        }
        return null;
    }
}
public class Main4 {
    public static void main(String[] args) throws Exception {
        AppContext appContext = new AppContext();
        Car car = (Car) appContext.getBean("car");      // by name으로 객체를 검색. (getBean 반환값이 Object이므로 (Car)로 형변환.)
        Engine engine = (Engine) appContext.getBean("engine");
        Door door = (Door) appContext.getBean(Door.class);  // by type으로 객체 검색.

        // ↓ 수동으로 객체를 연결해줌.
//        car.engine = engine;
//        car.door = door;

        System.out.println("car = " + car);
        System.out.println("engine = " + engine);
        System.out.println("door = " + door);
    }
}
