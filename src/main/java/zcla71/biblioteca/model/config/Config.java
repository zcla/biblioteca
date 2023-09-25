package zcla71.biblioteca.model.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zcla71.dao.seatable.config.SeaTableBase;

@NoArgsConstructor
@AllArgsConstructor
public class Config {
    @Getter @Setter
    private SeaTableBase biblioteca;
}
