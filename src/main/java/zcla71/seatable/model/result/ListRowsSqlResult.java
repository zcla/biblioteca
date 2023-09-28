package zcla71.seatable.model.result;

import java.util.Collection;
import java.util.Map;

import lombok.Data;

@Data
public class ListRowsSqlResult {
    private String error_message;
    private Boolean success;
    private Collection<Map<String, Object>> metadata;
    private Collection<Map<String, Object>> results;
}
