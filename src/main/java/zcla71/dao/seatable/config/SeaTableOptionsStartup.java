package zcla71.dao.seatable.config;

import lombok.Data;

@Data
public class SeaTableOptionsStartup {
    private Boolean recreateExistingTables;
    private Boolean createMissingTables;
    private Boolean createMissingColumns;
    private Boolean removeAlienTables;
    private Boolean eraseData;
}
