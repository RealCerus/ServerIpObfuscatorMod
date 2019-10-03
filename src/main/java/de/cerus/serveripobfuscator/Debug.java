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

package de.cerus.serveripobfuscator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Debug {

    public static void printFields(Class<?> clazz) {

        for (Field field : clazz.getFields()) {

        }

        for (Field field : clazz.getDeclaredFields()) {

        }
    }

    public static void printMethods(Class<?> clazz) {

        for (Method method : clazz.getMethods()) {
            System.out.println(">>> Method: " + method.getName() + " " + Arrays.stream(method.getParameters())
                    .map(parameter -> parameter.getType().getName()).collect(Collectors.joining(", "))
                    + " " + method.getReturnType().getName()+" ["+method.toString()+"]");
        }

        for (Method method : clazz.getDeclaredMethods()) {
            System.out.println(">>> Declared Method: " + method.getName() + " " + Arrays.stream(method.getParameters())
                    .map(parameter -> parameter.getType().getName()).collect(Collectors.joining(", "))
                    + " " + method.getReturnType().getName()+" ["+method.toString()+"]");
        }
    }
}
