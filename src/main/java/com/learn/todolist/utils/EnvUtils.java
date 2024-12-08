package com.learn.todolist.utils;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class EnvUtils {
    private static final Dotenv dotenv = Dotenv.load();

    public static String getEnv(String key) {
        return dotenv.get(key);
    }
}
