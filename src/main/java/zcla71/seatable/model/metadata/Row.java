package zcla71.seatable.model.metadata;

import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Row extends HashMap<String, Object> {
    public Row() {
    }

    public Row(Object[][] objects) {
        this.putAll(Stream.of(objects).collect(Collectors.toMap(object -> (String) object[0], object -> object[1])));
    }
}
