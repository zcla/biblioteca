package zcla71.seatable.model.metadata;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Row extends HashMap<String, Object> {
    public Row() {
    }

    public Row(Object[][] objects) {
        Map<String, Object> map = Stream.of(objects).filter(o -> (o[0] != null) && (o[1] != null)) .collect(Collectors.toMap(object -> (String) object[0], object -> object[1]));
        this.putAll(map);
    }
}
