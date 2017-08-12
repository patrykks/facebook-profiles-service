package pl.patrykks.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum Gender {
    MALE,
    FEMALE;

    private static Map<String, Gender> namesMap = new HashMap<>(2);

    static {
        namesMap.put("male", MALE);
        namesMap.put("female", FEMALE);
    }

    @JsonCreator
    public static Gender forValue(String value) {
        return namesMap.get(value.toLowerCase());
    }

    @JsonValue
    public String toValue() {
        return namesMap.entrySet().stream().
                filter(entry -> entry.getValue() == this).
                map(Map.Entry::getKey).
                findFirst().orElse(null);
    }
}
