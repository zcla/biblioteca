package zcla71.seatable.model.param;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class InsertColumnParam {
    @Getter @Setter
    private String table_name;
    @Getter @Setter
    private String column_name;
    @Getter @Setter
    private String column_type;
    @Getter @Setter
    private InsertColumnParamData column_data;
}
