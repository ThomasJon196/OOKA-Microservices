package de.hbrs;

import com.vaadin.flow.component.combobox.ComboBox;
import de.hbrs.data.entity.Test;

import java.io.*;
import java.util.Map;
import java.util.Properties;

public class ConfigurationManager {

    static File configFile = new File("config.properties");

    public static boolean saveConfig(Map<String, ComboBox> configuration) {
        Properties prop = new Properties();
        try {
            configFile.createNewFile();
            FileOutputStream out = new FileOutputStream(configFile);

            configuration.forEach((s, comboBox) -> {
                prop.put(s, comboBox.getValue());
            });

            prop.store(out, null);
            out.close();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public static boolean readConfig(Map<String, ComboBox> configuration) {
        Properties prop = new Properties();
        try {
            FileInputStream in =new FileInputStream(configFile);
            prop.load(in);

            configuration.forEach((s, comboBox) -> {
                String value = (String) prop.get(s);
                if (value != null) {
                    configuration.get(s).setValue(value);
                    //configuration.put(s, value);
                }
            });

            in.close();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

}
