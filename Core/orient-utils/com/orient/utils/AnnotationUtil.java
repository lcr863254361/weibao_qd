package com.orient.utils;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * AnnotationUtil
 *
 * @author Seraph
 *         2016-08-25 下午8:26
 */
public class AnnotationUtil {

    /**
     * change annotation value of key to a new value
     *
     * @param annotation
     * @param key
     * @param newValue
     * @return
     */
    public static Object changeAnnotationValue(Annotation annotation, String key, Object newValue) {
        Object handler = Proxy.getInvocationHandler(annotation);
        Field f;
        try {
            f = handler.getClass().getDeclaredField("memberValues");
        } catch (NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException(e);
        }
        f.setAccessible(true);

        Map<String, Object> memberValues;
        try {
            memberValues = (Map<String, Object>) f.get(handler);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }

        Object oldValue = memberValues.get(key);
        if (oldValue == null || oldValue.getClass() != newValue.getClass()) {
            throw new IllegalArgumentException();
        }
        memberValues.put(key, newValue);
        return oldValue;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface ClassAnnotation {
        String value() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface FieldAnnotation {
        String value() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface MethodAnnotation {
        String value() default "";
    }

    @ClassAnnotation("class test")
    public static class TestClass {
        @FieldAnnotation("field test")
        public Object field;

        @MethodAnnotation("method test")
        public void method() {

        }
    }

    public static void main(String[] args) throws Exception {

        final ClassAnnotation classAnnotation = TestClass.class.getAnnotation(ClassAnnotation.class);
        System.out.println("old ClassAnnotation = " + classAnnotation.value());
        changeAnnotationValue(classAnnotation, "value", "another class annotation value");

        final ClassAnnotation classAnnotation2 = TestClass.class.getAnnotation(ClassAnnotation.class);
        System.out.println("modified ClassAnnotation = " + classAnnotation.value());
        System.out.println("modified ClassAnnotation = " + classAnnotation2.value());

        Field field = TestClass.class.getField("field");
        final FieldAnnotation fieldAnnotation = field.getAnnotation(FieldAnnotation.class);
        System.out.println("old FieldAnnotation = " + fieldAnnotation.value());
        changeAnnotationValue(fieldAnnotation, "value", "another field annotation value");
        System.out.println("modified FieldAnnotation = " + fieldAnnotation.value());

        Method method = TestClass.class.getMethod("method");
        final MethodAnnotation methodAnnotation = method.getAnnotation(MethodAnnotation.class);
        System.out.println("old MethodAnnotation = " + methodAnnotation.value());
        changeAnnotationValue(methodAnnotation, "value", "another method annotation value");
        System.out.println("modified MethodAnnotation = " + methodAnnotation.value());
    }
}
