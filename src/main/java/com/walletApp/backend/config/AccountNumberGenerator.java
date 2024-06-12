package com.walletApp.backend.config;

import java.util.Random;

public class AccountNumberGenerator {
    public static String generateAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            accountNumber.append(random.nextInt(10));
        }
        System.out.println(accountNumber.toString());
        return accountNumber.toString();
    }

    public static String formatAccountNumber(String accountNumber) {
        if (accountNumber == null) {
            return "";
        }

        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < accountNumber.length(); i++) {
            if (i > 0 && i % 4 == 0) {
                formatted.append(" ");
            }
            formatted.append(accountNumber.charAt(i));
        }
        return formatted.toString();
    }

}
