package com.overlake.ftc.ftcrobothub.configuration;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Configuration
{
    public Map<String, Object> configuration;

    public Configuration() {
        this.configuration = new HashMap<>();
    }

    public Configuration(Map<String, Object> configuration) {
        this.configuration = configuration;
    }

    public void save(String name) {
        ConfigurationBuilder.ensureConfigurationDirectoryExists();
        File file = new File(ConfigurationBuilder.ConfigurationPath + "/" + name + ".txt");
        writeToFile(file);
    }

    private void writeToFile(File file) {
        try
        {
            Gson gson = new Gson();
            FileWriter writer = new FileWriter(file);
            writer.write(gson.toJson(this));
            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
