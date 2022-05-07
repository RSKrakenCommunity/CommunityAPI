package com.rshub.api.widgets.trading;

import com.rshub.api.widgets.trade.TradeWidget;

public final class TradeHelper {
    private TradeHelper(){}

    public static boolean isOpen() {
        return TradeWidget.INSTANCE.isOpen();
    }

    public static void accept() {
        TradeWidget.INSTANCE.accept();
    }

    public static void decline() {
        TradeWidget.INSTANCE.decline();
    }

    public static void offerAll() {
        TradeWidget.INSTANCE.offerAll();
    }

    public static void offerCoin(int amount) {
        TradeWidget.INSTANCE.offerCoin(amount);
    }
}
