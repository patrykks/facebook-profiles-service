package pl.patrykks.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum Relationship {
    MARRIED,
    SINGLE,
    UNKNOWN;

    private static final Map<String, Relationship> namesMap = new HashMap<>(2);

    static {
        namesMap.put("married", MARRIED);
        namesMap.put("single", SINGLE);
    }

    @JsonCreator
    public static Relationship forValue(String value) {
        return namesMap.getOrDefault(value.toLowerCase(), UNKNOWN);
    }

    @JsonValue
    public String toValue() {
        return namesMap.entrySet().stream().
                filter(entry -> entry.getValue() == this).
                map(Map.Entry::getKey).
                findFirst().orElse("unknown");
    }
}
