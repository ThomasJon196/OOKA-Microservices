package de.hbrs.control;

import com.vaadin.flow.component.combobox.ComboBox;
import de.hbrs.model.State;
import de.hbrs.model.Test;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ConfigurationBuilder {

    public static String buildConfig(Map<String, ComboBox> configuration) {
        JSONObject config = new JSONObject();
        config.put("request_id", String.valueOf(UUID.randomUUID()));

        Map<String, String> map_liquid = new HashMap<>();
        map_liquid.put("oil", getValue(configuration, Test.OIL));
        map_liquid.put("fuel", getValue(configuration, Test.FUEL));
        config.put("category_liquid", map_liquid);

        Map<String, String> map_motor = new HashMap<>();
        map_motor.put("gearbox", getValue(configuration, Test.GEARBOX));
        map_motor.put("engine", getValue(configuration, Test.ENGINE));
        map_motor.put("starting_system", getValue(configuration, Test.STARTING_SYSTEM));
        config.put("category_motor", map_motor);

        Map<String, String> map_software = new HashMap<>();
        map_software.put("monitoring", getValue(configuration, Test.TEST_MONITORING));
        map_software.put("fuel", getValue(configuration, Test.TEST_GEARBOX));
        config.put("category_software", map_software);

        return config.toString();
    }

    private static String getValue(Map<String, ComboBox> configuration, Test test) {
        return configuration.get(test.name()).getValue().toString();
    }

    public static String buildResponse(UUID request_id, String equipment_name, String equipment_status) {
        JSONObject config = new JSONObject();
        config.append("request_id", request_id);

        Map<String, String> map_equipment = new HashMap<>();
        map_equipment.put("name", equipment_name);
        map_equipment.put("result", equipment_status);
        config.put("equipment", map_equipment);

        return config.toString();
    }

    public record ConfigurationResponse(UUID request_id, String equipmentName, State equipmentState) {}

    public static ConfigurationResponse readResponse(String responseString) {
        try {
            JSONObject response = new JSONObject(responseString);
            UUID request_id = UUID.fromString(response.getString("request_id"));
            JSONObject equipment = response.getJSONObject("equipment");
            String equipmentName = equipment.getString("name");
            String state = equipment.getString("result");
            State equipmentState = State.valueOf(state);

            return new ConfigurationResponse(request_id, equipmentName, equipmentState);

        } catch (JSONException | IllegalArgumentException e) {
            // ignore - response not correct
            System.out.println("Falsches Response-Format: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
