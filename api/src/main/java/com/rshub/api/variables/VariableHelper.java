package com.rshub.api.variables;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class VariableHelper {
    private VariableHelper() {}

    private static final Map<String, Variable> VARIABLES = new HashMap<>();

    public static void registerVariable(@NotNull String name, Variable variable) {
        Objects.requireNonNull(name);
        VARIABLES.put(name, variable);
    }

    public static int getVariableByName(@NotNull String name) {
        Objects.requireNonNull(name);
        if(VARIABLES.containsKey(name)) {
            return VARIABLES.get(name).getValue();
        }
        return 0;
    }
}
