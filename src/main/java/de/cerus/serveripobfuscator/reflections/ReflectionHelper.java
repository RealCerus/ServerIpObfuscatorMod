/*
 *  Copyright (c) 2018 Cerus
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Cerus
 *
 */

package de.cerus.serveripobfuscator.reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectionHelper {

    private ReflectionHelper() {
        throw new UnsupportedOperationException();
    }

    public static Object getSilent(Object clazz, String fieldName) {
        return getSilent(clazz, fieldName, false);
    }

    public static Object getSilent(Object clazz, String fieldName, boolean superClass) {
        try {
            return get(clazz, fieldName, superClass);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object get(Object clazz, String fieldName) throws IllegalAccessException, NoSuchFieldException {
        return get(clazz, fieldName, false);
    }

    public static Object get(Object clazz, String fieldName, boolean superClass) throws IllegalAccessException, NoSuchFieldException {
        Class<?> aClass = superClass ? clazz.getClass().getSuperclass() : clazz.getClass();
        Field field = aClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(clazz);
    }

    public static void setSilent(Object clazz, String fieldName, Object newValue) {
        setSilent(clazz, fieldName, newValue, false);
    }

    public static void setSilent(Object clazz, String fieldName, Object newValue, boolean superClass) {
        try {
            set(clazz, fieldName, newValue, superClass);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void set(Object clazz, String fieldName, Object newValue) throws NoSuchFieldException, IllegalAccessException {
        set(clazz, fieldName, newValue, false);
    }

    public static void set(Object clazz, String fieldName, Object newValue, boolean superClass) throws NoSuchFieldException, IllegalAccessException {
        Class<?> aClass = superClass ? clazz.getClass().getSuperclass() : clazz.getClass();
        Field field = aClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(clazz, newValue);
    }

    public static Object invokeSilent(Object clazz, String methodName, Object[] parameters) {
        return invokeSilent(clazz, methodName, false, parameters);
    }

    public static Object invokeSilent(Object clazz, String methodName, boolean superClass, Object[] parameters) {
        try {
            return invoke(clazz, methodName, superClass, parameters);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Object invoke(Object clazz, String methodName, Object[] parameters) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invoke(clazz, methodName, false, parameters);
    }

    public static Object invoke(Object clazz, String methodName, boolean superClass, Object[] parameters) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?>[] parameterClasses = Arrays.stream(parameters)
                .map(Object::getClass)
                .toArray(Class<?>[]::new);

        Class<?> aClass = superClass ? clazz.getClass().getSuperclass() : clazz.getClass();
        Method method = aClass.getDeclaredMethod(methodName, parameterClasses);
        method.setAccessible(true);
        return method.invoke(clazz, parameters);
    }
}
