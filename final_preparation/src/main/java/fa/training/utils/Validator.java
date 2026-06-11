package fa.training.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Validator {
    public static boolean isValidEmail(String email) {
        String regex =  "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(regex);
    }

    public static boolean isValidPhone(String phone) {
        String regex = "^[0-9]{10}$";
        return phone.matches(regex);
    }

    public static boolean isWithinRange(double value, double min, double max) {
        return value >= min && value <= max;
    }

    /**
        Example: "2026-06-07" -"yyyy-MM-dd"
     */
    public static boolean isValidLocalDate(String value, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            LocalDate.parse(value, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
        2026-06-07 15:30:00
        yyyy-MM-dd HH:mm:ss
     */
    public static boolean isValidLocalDateTime(String value,String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            LocalDateTime.parse(value, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
