package com.spring.ch3.diCopy3;


import com.google.common.reflect.ClassPath;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
class Car {}
@Component
class SportsCar extends Car {}
@Component
class Truck extends Car {}
@Component
class Engine {}

class AppContext {
    Map map;        // 객체들을 저장할 객체 저장소.

    AppContext() {
        map = new HashMap();
        doComponentScan();      // @Component 에노테이션이 붙은 클래스들을 객체로 생성해서 Map에 저장하는 메서드.
    }

    private void doComponentScan() {
        try {
            // 1. 패키지 내의 클래스 목록을 가져옴.
            ClassLoader classLoader = AppContext.class.getClassLoader();
            ClassPath classPath = ClassPath.from(classLoader);
            Set<ClassPath.ClassInfo> classInfoSet = classPath.getTopLevelClasses("com.spring.ch3.diCopy3");
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
public class Main3 {
    public static void main(String[] args) throws Exception {
        AppContext appContext = new AppContext();
        Car carByName = (Car) appContext.getBean("car");      // by name으로 객체를 검색. (getBean 반환값이 Object이므로 (Car)로 형변환.)
        Car carByType = (Car) appContext.getBean(Car.class);      // by type으로 객체를 검색. (getBean 반환값이 Object이므로 (Car)로 형변환.)
        System.out.println("car = " + carByName);
        System.out.println("carByType = " + carByType);
        Engine engine = (Engine) appContext.getBean("engine");
        System.out.println("engine = " + engine);
    }
}
