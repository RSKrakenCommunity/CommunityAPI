package com.rshub.api.kraken;

import kraken.plugin.api.Client;
import kraken.plugin.api.Player;

public enum ReportHelper {
    REPORT_EXPLOITING_BUGS(5),
    REPORT_STAFF_IMPERSONATION(6),
    REPORT_BUYING_OR_SELLING_ACCOUNT(7),
    REPORT_MACROING_OR_USE_OF_BOTS(8),
    REPORT_ENCOURAGING_RULE_BREAKING(10),
    REPORT_ADVERTISING_WEBSITES(12),
    REPORT_ASKING_FOR_CONTACT_INFO(14),
    REPORT_SCAMMING(16),
    REPORT_OFFENSIVE_LANGUAGE(17),
    REPORT_SOLICITATION(18),
    REPORT_DISRUPTIVE_BEHAVIOR(19),
    REPORT_OFFENSIVE_NAME(20),
    REPORT_REAL_LIFE_THREATS(21),
    REPORT_BREAKING_REAL_LAWS(22),
    REPORT_GAMES_OF_CHANCE(23)
    ;

    private final int id;

    ReportHelper(int id) {
        this.id = id;
    }

    public void report(Player player) {
        report(player.getName());
    }

    public void report(String player) {
        Client.report(id, player);
    }
}
