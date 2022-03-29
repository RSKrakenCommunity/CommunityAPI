package com.rshub.api.kraken;

import kraken.plugin.api.Client;
import kraken.plugin.api.Kraken;

public final class GameWorldHelper {
    private GameWorldHelper() {}

    public static void hopWorld(int world) {
        Kraken.setLoginWorld(world);
        Kraken.toggleAutoLogin(true);
        Client.exitToLobby();
    }
}
