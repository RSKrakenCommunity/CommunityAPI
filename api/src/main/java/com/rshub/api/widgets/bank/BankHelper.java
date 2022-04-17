package com.rshub.api.widgets.bank;

import com.rshub.api.banking.Bank;

public final class BankHelper {
    private BankHelper() {
    }

    private final static Bank BANK = new Bank();

    public static boolean isOpen() {
        return BANK.isOpen();
    }

    public static int getBankTabNumber() {
        return BANK.getBankTab();
    }

    public static boolean isInventoryOpen(SideInventory bsi) {
        return BANK.getInvTab() == bsi.getValue();
    }

    public static void close() {
        BANK.closeBank();
    }

    public static int getTransferXAmount() {
        return BANK.getTransferX();
    }

    public static int getTransferAmount() {
        return BANK.getTransferAmount();
    }

    public static int getSelectedPreset() {
        return BANK.getSelectedPreset();
    }

    public static boolean isPresetInventory() {
        return BANK.isPresetInv();
    }

    public static boolean isPresetEquipment() {
        return BANK.isPresetEquipment();
    }

    public static boolean isPresetBeast() {
        return BANK.isSumInv();
    }

    public static void withdrawItem(int itemId, int amount) {
        BANK.withdrawItem(itemId, amount);
    }

    public static void depositItem(int itemId, int amount) {
        BANK.depositItem(itemId, amount);
    }

    public static void depositInventory(boolean useKey) {
        BANK.depositInventory(useKey);
    }

    public static void depositEquipment(boolean useKey) {
        BANK.depositEquipment(useKey);
    }

    public static void depositBurden(boolean useKey) {
        BANK.depositBurden(useKey);
    }

    public static void depositALL(boolean useKey) {
        BANK.depositALL(useKey);
    }

    public static void togglePlaceholders() {
        BANK.togglePlaceholders();
    }

    public static void toggleNotes() {
        BANK.toggleNotes();
    }
}
