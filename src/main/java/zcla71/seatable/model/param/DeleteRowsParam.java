package zcla71.seatable.model.param;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class DeleteRowsParam {
    @Getter @Setter
    private String table_name;
    @Getter @Setter
    private Collection<String> row_ids;
}
