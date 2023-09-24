package zcla71.seatable.model.param;

import java.util.Collection;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class CreateRowLinksBatchParam {
    @Getter @Setter
    private String table_id;
    @Getter @Setter
    private String other_table_id;
    @Getter @Setter
    private String link_id;
    @Getter @Setter
    private Collection<String> row_id_list;
    @Getter @Setter
    private Map<String, Collection<String>> other_rows_ids_map;
}
