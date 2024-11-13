package org.hybridai.password;

import jakarta.inject.Singleton;

@Singleton
public class DroolsPasswordRewriter {

    public String rewritePassword(String s) {
        if (!s.matches(".*[\\W_].*")) {
            if (s.contains("e")) {
                s = s.replace('e', '&');
            } else if (s.contains("a")) {
                s = s.replace('a', '@');
            } else if (s.contains("i")) {
                s = s.replace('i', '!');
            } else {
                s = "#" + s;
            }
        }

        if (!s.matches(".*\\d.*")) {
            if (s.contains("e")) {
                s = s.replace('e', '3');
            } else if (s.contains("o")) {
                s = s.replace('o', '0');
            } else if (s.contains("i")) {
                s = s.replace('i', '1');
            } else {
                s = new java.util.Random().nextInt(0,9) + s;
            }
        }

        if (s.contains(" ")) {
            s = s.replace(' ', '_');
        }

        if (s.length() > 10) {
            s = s.substring(0, 8);
        }

        if (s.length() < 8) {
            s = s + new java.util.Random().nextInt(0,9);
        }

        return s;
    }
}
