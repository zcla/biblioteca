package zcla71.seatable.model.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class CreateRowLinkParam {
    @Getter @Setter
    private String table_name;
    @Getter @Setter
    private String other_table_name;
    @Getter @Setter
    private String link_id;
    @Getter @Setter
    private String table_row_id;
    @Getter @Setter
    private String other_table_row_id;
}
