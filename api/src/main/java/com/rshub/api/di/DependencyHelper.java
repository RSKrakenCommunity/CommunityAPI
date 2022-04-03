package com.rshub.api.di;

public final class DependencyHelper {
    private DependencyHelper() {}

    private static final DependencyInjection DEPENDENCY_INJECTION = new DependencyInjection();

    public static <T> T get(Class<T> clazz) {
        return DEPENDENCY_INJECTION.get(clazz);
    }

}
