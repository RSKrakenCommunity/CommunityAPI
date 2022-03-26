package com.rshub.api.variables;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class VariableHelper {
    private VariableHelper() {
    }

    private static final Map<String, Variable> VARIABLES = new HashMap<>();

    public static void registerVariable(@NotNull String name, Variable variable) {
        Objects.requireNonNull(name);
        VARIABLES.put(name, variable);
    }

    public static int getVariable(@NotNull String nspace, @NotNull String name) {
        String key = nspace + ":" + name;
        return getVariableByName(key);
    }

    public static Map<String, Variable> getVariables(@NotNull String nspace) {
        Map<String, Variable> variables = new HashMap<>();
        for (String key : VARIABLES.keySet()) {
            String[] values = key.split(":");
            String nsp = values[0];
            String nme = values[1];
            if (nsp.equalsIgnoreCase(nspace)) {
                variables.put(nme, VARIABLES.get(key));
            }
        }
        return variables;
    }

    public static int getVariableByName(@NotNull String name) {
        Objects.requireNonNull(name);
        if (VARIABLES.containsKey(name)) {
            return VARIABLES.get(name).getValue();
        }
        return 0;
    }
}
