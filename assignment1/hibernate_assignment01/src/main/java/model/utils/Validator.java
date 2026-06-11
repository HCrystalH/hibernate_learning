package model.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Validator {
    public static boolean isValidPhoneNumber(String input){
        if(input == null || input.trim().isEmpty()) return false;

        // remove spaces and dashes
        input = input.replaceAll("[\\s-]", "");

        return input.matches(Constant.PHONE_REGEX);
    }

    public static boolean isWithinRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    public static boolean isValidGender(String input){
        if(input == null || input.trim().isEmpty()) return false;

        return input.trim().toLowerCase().matches(Constant.MALE) ||
                input.trim().toLowerCase().matches(Constant.FEMALE);
    }

    public static boolean isValidNumber(String input){
        if (input == null || input.trim().isEmpty())  return false;

        try {
            Integer.parseInt(input.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidDate(String input){
        if (input == null || input.trim().isEmpty())  return false;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constant.DATE_FORMAT);

        try {
            LocalDate.parse(input.trim(), formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
