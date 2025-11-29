package com.clinicturn.api.patient.util;

public class TurnNumberGenerator {

    private static final char[] LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final int MAX_NUMBER = 999;

    public static String getNextTurnNumber(String lastNumber) {
        if (lastNumber == null || lastNumber.isBlank()) {
            return "A-001";
        }

        String[] parts = lastNumber.split("-");
        char currentLetter = parts[0].charAt(0);
        int currentNumber = Integer.parseInt(parts[1]);

        currentNumber++;

        if (currentNumber > MAX_NUMBER) {
            currentLetter = getNextLetter(currentLetter);
            currentNumber = 1;
        }

        return String.format("%s-%03d", currentLetter, currentNumber);
    }

    private static char getNextLetter(char currentLetter) {
        if (currentLetter == 'Z') {
            return 'A';
        }
        return (char) (currentLetter + 1);
    }
}
