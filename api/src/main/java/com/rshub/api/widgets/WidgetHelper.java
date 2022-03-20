package com.rshub.api.widgets;

public class WidgetHelper {
    private WidgetHelper() {}

    public static int hash(int parentId, int childId) {
        return (parentId << 16) + childId;
    }
}
