package org.jannick.dribble;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    public static String getToken() {
        Dotenv dotenv = Dotenv.load();
        return dotenv.get("TOKEN");
    }
}
