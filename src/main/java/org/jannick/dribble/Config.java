package org.jannick.dribble;

public class Config {
    public static String getToken() {
        return System.getenv("TOKEN");
    }
}
