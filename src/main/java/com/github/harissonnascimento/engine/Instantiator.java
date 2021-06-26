package com.github.harissonnascimento.engine;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.SneakyThrows;

public class Instantiator {

  protected Instantiator() {
  }

  private static <H> List<Method> getSetMethodsName(Class<H> clazz) {

    Method[] methods = clazz.getMethods();

    List<Method> methodList = Arrays.asList(methods);

    return methodList.stream().filter(m -> m.getName().startsWith("set")).collect(Collectors.toList());

  }

  private static <H> List<Method> getReturnTypesMethods(Class<H> clazz) {

    Method[] methods = clazz.getMethods();

    List<Method> methodList = Arrays.asList(methods);

    return methodList.stream()
        .filter(
            m -> m.getName().startsWith("get") || m.getName().startsWith("is")
        )
        .collect(Collectors.toList());

  }

  @SneakyThrows
  public static <H> H generateClass(Class<H> typeClass) {

    Constructor<H> constructor = typeClass.getConstructor();
    H clazz = constructor.newInstance();

    List<Method> setMethodsName = getSetMethodsName(typeClass);

    List<Method> returnTypesMethods = getReturnTypesMethods(typeClass);

    for (Method setMethod : setMethodsName) {

      for (Method getMethod : returnTypesMethods) {

        String name = setMethod.getName();

        name = name.replace("set", "get");

        if (getMethod.getName().contains(name)) {

          Class<?> returnType = getMethod.getReturnType();

          verifyType(returnType, setMethod, typeClass, clazz, name);

        }

      }

    }
    return clazz;
  }

  @SneakyThrows
  private static <H> void verifyType(Class<?> returnType, Method setMethod, Class<H> typeClass, H clazz,
      String name) {

    Random random = new SecureRandom();

    if (returnType.isAssignableFrom(int.class)) {

      var declaredMethod = typeClass.getDeclaredMethod(setMethod.getName(), int.class);

      declaredMethod.invoke(clazz, random.nextInt());

      return;

    }

    if (returnType.isAssignableFrom(Integer.class)) {

      var declaredMethod = typeClass.getDeclaredMethod(setMethod.getName(), Integer.class);

      declaredMethod.invoke(clazz, random.nextInt());

      return;

    }

    char c = UUID.randomUUID().toString().toCharArray()[0];

    if (returnType.isAssignableFrom(char.class)) {

      var declaredMethod = typeClass.getDeclaredMethod(setMethod.getName(), char.class);

      declaredMethod.invoke(clazz, c);

      return;

    }

    if (returnType.isAssignableFrom(Character.class)) {

      var declaredMethod = typeClass.getDeclaredMethod(setMethod.getName(), Character.class);

      declaredMethod.invoke(clazz, c);

      return;

    }

    if (returnType.isAssignableFrom(double.class)) {

      var declaredMethod = typeClass.getDeclaredMethod(setMethod.getName(), double.class);

      declaredMethod.invoke(clazz, random.nextDouble());

      return;

    }

    if (returnType.isAssignableFrom(Double.class)) {

      var declaredMethod = typeClass.getDeclaredMethod(setMethod.getName(), Double.class);

      declaredMethod.invoke(clazz, random.nextDouble());

      return;

    }

    if (returnType.isAssignableFrom(float.class)) {

      var declaredMethod = typeClass.getDeclaredMethod(setMethod.getName(), float.class);

      declaredMethod.invoke(clazz, random.nextFloat());

      return;

    }

    if (returnType.isAssignableFrom(Float.class)) {

      var declaredMethod = typeClass.getDeclaredMethod(setMethod.getName(), Float.class);

      declaredMethod.invoke(clazz, random.nextFloat());

      return;

    }

    if (returnType.isAssignableFrom(long.class)) {

      var declaredMethod = typeClass.getDeclaredMethod(setMethod.getName(), long.class);

      declaredMethod.invoke(clazz, random.nextLong());

      return;

    }

    if (returnType.isAssignableFrom(Long.class)) {

      var declaredMethod = typeClass.getDeclaredMethod(setMethod.getName(), Long.class);

      declaredMethod.invoke(clazz, random.nextLong());

      return;

    }

    if (returnType.isAssignableFrom(short.class)) {

      var declaredMethod = typeClass.getDeclaredMethod(setMethod.getName(), short.class);

      declaredMethod.invoke(clazz, (short) random.nextInt(Short.MAX_VALUE));

      return;

    }

    if (returnType.isAssignableFrom(Short.class)) {

      var declaredMethod = typeClass.getDeclaredMethod(setMethod.getName(), Short.class);

      declaredMethod.invoke(clazz, (short) random.nextInt(Short.MAX_VALUE));

      return;

    }

    if (returnType.isAssignableFrom(boolean.class)) {

      var declaredMethod = typeClass.getDeclaredMethod(setMethod.getName(), boolean.class);

      declaredMethod.invoke(clazz, random.nextBoolean());

      return;

    }

    if (returnType.isAssignableFrom(Boolean.class)) {

      var declaredMethod = typeClass.getDeclaredMethod(setMethod.getName(), Boolean.class);

      declaredMethod.invoke(clazz, random.nextBoolean());

      return;

    }

    if (returnType.isAssignableFrom(byte.class)) {

      var declaredMethod = typeClass.getDeclaredMethod(setMethod.getName(), byte.class);

      var randomByte = "random";

      declaredMethod.invoke(clazz, randomByte.getBytes(StandardCharsets.UTF_8)[0]);

      return;

    }

    if (returnType.isAssignableFrom(Byte.class)) {

      var declaredMethod = typeClass.getDeclaredMethod(setMethod.getName(), Byte.class);

      var randomByte = "random";

      declaredMethod.invoke(clazz, randomByte.getBytes(StandardCharsets.UTF_8)[0]);

      return;

    }

    if (returnType.isAssignableFrom(String.class)) {

      var declaredMethod = typeClass.getDeclaredMethod(setMethod.getName(), String.class);

      declaredMethod.invoke(clazz, UUID.randomUUID().toString());

      return;

    }

    if (Collection.class.isAssignableFrom(returnType)) {

      name = name.replace("get", "");

      name = name.substring(0, 1).toLowerCase().concat(name.substring(1));

      var parameterizedType = (ParameterizedType) typeClass.getDeclaredField(name).getGenericType();

      Class<?> actualTypeArgument = (Class<?>) parameterizedType.getActualTypeArguments()[0];

      Constructor<?> constructor = actualTypeArgument.getConstructor();
      Class<?> declaringClass = constructor.getDeclaringClass();

      Object o = generateClass(declaringClass);

      Class<?> parameterType = setMethod.getParameterTypes()[0];

      if (Set.class.isAssignableFrom(parameterType)){
        Collection<Object> list;

        list = new HashSet<>();
        list.add(o);

        var declaredMethod = typeClass.getDeclaredMethod(setMethod.getName(), o != null ?  parameterType: null);

        declaredMethod.invoke(clazz, list);

        return;
      }

      if (List.class.isAssignableFrom(parameterType)){
        Collection<Object> list;

        list = new ArrayList<>();
        list.add(o);

        var declaredMethod = typeClass.getDeclaredMethod(setMethod.getName(), o != null ?  parameterType: null);

        declaredMethod.invoke(clazz, list);

      }

      return;

    }

    Constructor<?> returnContructor = returnType.getConstructor();
    Class<?> declaringClass = returnContructor.getDeclaringClass();

    Object o = generateClass(declaringClass);

    var declaredMethod = typeClass.getDeclaredMethod(setMethod.getName(), o != null ? o.getClass() : null);

    declaredMethod.invoke(clazz, o);

  }

}





