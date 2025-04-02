package yeoun.util;

public class FormattingUtil {

    private FormattingUtil() {
    }

    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber != null && phoneNumber.startsWith("+")) {
            return "010" + phoneNumber.substring(3);
        }
        return phoneNumber;
    }

    public static String cleanText(String text) {
        return text
                .replaceAll("\\s+", "")
                .replaceAll("[\\u200B-\\u200D\\uFEFF]", "")
                .trim();
    }

}
