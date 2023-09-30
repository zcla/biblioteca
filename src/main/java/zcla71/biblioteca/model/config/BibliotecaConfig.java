package zcla71.biblioteca.model.config;

import lombok.Data;
import zcla71.dao.seatable.config.SeaTableConfig;

@Data
public class BibliotecaConfig {
    private LibibConfig libib;
    private SeaTableConfig seaTable;
}
