package zcla71.seatable.model.result;

import java.util.Collection;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class ListRowsSqlResult {
    @Getter @Setter
    private String error_message;
    @Getter @Setter
    private Boolean success;
    @Getter @Setter
    private Collection<Map<String, Object>> metadata;
    @Getter @Setter
    private Collection<Map<String, Object>> results;
}
