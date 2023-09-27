package zcla71.seatable.model.metadata;

import lombok.Data;

@Data
public class ViewGroupBy {
    private String column_key;
    private String sort_type;
    private String count_type;
}
