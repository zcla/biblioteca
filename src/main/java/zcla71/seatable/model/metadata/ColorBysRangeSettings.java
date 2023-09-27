package zcla71.seatable.model.metadata;

import lombok.Data;

@Data
public class ColorBysRangeSettings {
    private String color_type;
    private Boolean is_custom_start_value;
    private Boolean is_custom_end_value;
    private String start_value;
    private String end_value;
}
