package com.sweettracker.utils;

public class Utils {

    public static final int ENTRY_LOCALES = 0X009;
    public static final int ENTRY_THEMES = 0x010;

    private Utils() {
    }

    public static String getEntryText(int entryId, int entryIndex) {
        StringBuffer text = new StringBuffer();
        switch (entryId) {
            case ENTRY_LOCALES: {
                if (entryIndex == 0) {
                    text.append("en-GB");
                } else if (entryIndex == 1) {
                    text.append("fr-FR");
                } else if (entryIndex == 2) {
                    text.append("it-IT");
                } else if (entryIndex == 3) {
                    text.append("de-DE");
                } else if (entryIndex == 4) {
                    text.append("es-ES");
                }
                break;
            }
            case ENTRY_THEMES: {
                if (entryIndex == 0) {
                    text.append("themeDefault");
                } else if (entryIndex == 1) {
                    text.append("themeAutumn");
                } else if (entryIndex == 2) {
                    text.append("themeWinter");
                }
                break;
            }
        }
        return text.toString();
    }
}
