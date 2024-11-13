package org.hybridai.password;

import jakarta.inject.Singleton;

@Singleton
public class DroolsPasswordRewriter {

    public String rewritePassword(String s) {
        if (s.matches(".*[\\W_].*")) {
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

        if (s.contains(" ")) {
            s = s.replace(' ', '_');
        }

        if (s.length() > 10) {
            s = s.substring(0, 8);
        }

        return s;
    }
}
