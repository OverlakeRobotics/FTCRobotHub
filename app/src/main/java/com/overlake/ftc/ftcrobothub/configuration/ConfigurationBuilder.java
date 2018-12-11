package com.overlake.ftc.ftcrobothub.configuration;

import android.os.Environment;

import com.google.gson.Gson;
import com.overlake.ftc.ftcrobothub.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConfigurationBuilder
{
    public static final String ConfigurationPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/FTCRobotHub/configurations";

    public static Configuration buildFromFile(String configurationName) {
        ensureConfigurationDirectoryExists();
        Scanner rawInput = getRawInput(configurationName);
        StringBuilder stringBuilder = new StringBuilder();
        while (rawInput.hasNextLine()) {
            stringBuilder.append(rawInput.nextLine());
        }
        return buildFromJSON(stringBuilder.toString());
    }

    public static void ensureConfigurationDirectoryExists()
    {
        File configurationDirectory = new File(ConfigurationPath);
        if (!configurationDirectory.exists()) {
            configurationDirectory.mkdirs();
        }
    }

    private static Scanner getRawInput(String configurationName) {
        try
        {
            return new Scanner(new File(ConfigurationPath + "/" + configurationName + ".txt"));
        }
        catch (FileNotFoundException e)
        {
            throw new IllegalArgumentException("Configuration file " + configurationName + " does not exist", e);
        }
    }

    public static Configuration buildFromJSON(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Configuration.class);
    }
}
