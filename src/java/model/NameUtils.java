package model;


public class NameUtils {
    public static String[] splitFullName(String fullName) {
        String[] result = new String[]{"", ""};
        if (fullName == null || fullName.trim().isEmpty()) {
            return result;
        }
        String[] nameParts = fullName.trim().split("\\s+");
        if (nameParts.length == 1) {
            result[0] = nameParts[0];
            result[1] = "";
            return result;
        }
        result[0] = nameParts[nameParts.length - 1];
        StringBuilder lastNameBuilder = new StringBuilder();
        for (int i = 0; i < nameParts.length - 1; ++i) {
            lastNameBuilder.append(nameParts[i]);
            if (i >= nameParts.length - 2) continue;
            lastNameBuilder.append(" ");
        }
        result[1] = lastNameBuilder.toString();
        return result;
    }
}