package zcla71.seatable.model.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zcla71.seatable.model.metadata.Row;

@NoArgsConstructor
@AllArgsConstructor
public class AddRowParam {
    @Getter @Setter
    private Row row;
    @Getter @Setter
    private String table_name;
}
