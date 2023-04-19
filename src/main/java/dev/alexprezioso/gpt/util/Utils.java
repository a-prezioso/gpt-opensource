package dev.alexprezioso.gpt.util;

public class Utils {
    public static String formatCodeInText(String text) {
        return text.replaceAll("<code>(.*?)</code>", "$1");
    }
}
