package ksch.util;

import lombok.SneakyThrows;

import java.lang.reflect.Method;

public class TypeConverter<S> {

    private final Class<S> commonInterface;

    public TypeConverter(Class<S> commonInterface) {
        if (!commonInterface.isInterface()) {
            throw new IllegalArgumentException("This type converter only supports conversion between two classes" +
                    "which implement the same interface. Type is not an interface: " + commonInterface.getTypeName());
        }
        for (Method m : commonInterface.getDeclaredMethods()) {
            if (m.getParameterCount() > 0) {
                throw new IllegalArgumentException("This type converter can only be used for base interfaces which " +
                        "implement only getter methods. Method which violates this constraint: " + m.getName());
            }
            var methodName = m.getName();
            if (!(methodName.startsWith("get") || methodName.startsWith("is"))) {
                throw new IllegalArgumentException("This type converter can only be used for base interfaces which " +
                        "implement only getter methods which start with prefix 'get' or 'is'. Method which violates " +
                        "this constraint: " + methodName);
            }
        }

        this.commonInterface = commonInterface;
    }

    @SneakyThrows
    public <T extends S> T convertTo(S source, Class<T> target) {
        var targetConstructor = target.getDeclaredConstructor();
        targetConstructor.setAccessible(true);
        T targetInstance = targetConstructor.newInstance();

        for (Method getter : commonInterface.getDeclaredMethods()) {
            getter.setAccessible(true);
            var value = getter.invoke(source);
            if (value != null) {
                var setter = findSetter(target, getter);
                setter.setAccessible(true);
                setter.invoke(targetInstance, value);
            }
        }

        return targetInstance;
    }

    private static <T> Method findSetter(Class<T> target, Method getter) {
        var getterName = getter.getName();

        String setterName = null;
        if (getterName.startsWith("get")) {
            setterName = getterName.replace("get", "set");
        } else {
            setterName = getterName.replace("is", "set");
        }

        for (Method method : target.getDeclaredMethods()) {
            if (method.getName().equals(setterName)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Cannot find corresponding setter for getter '" + getterName + "()' in type '"
                + target.getTypeName() + "'.");
    }
}
