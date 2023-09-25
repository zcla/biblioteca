package zcla71.dao.seatable.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class SeaTableOptionsStartup {
    @Getter @Setter
    private Boolean recreateExistingTables;
    @Getter @Setter
    private Boolean createMissingTables;
    @Getter @Setter
    private Boolean createMissingColumns;
    @Getter @Setter
    private Boolean removeAlienTables;
    @Getter @Setter
    private Boolean eraseData;
}
