package zcla71.dao.seatable;

import java.util.Collection;

import zcla71.dao.config.SeaTableBase;

public class SeaTableConfig { // TODO Mudar para Config
    private Collection<SeaTableBase> bases;

    public Collection<SeaTableBase> getBases() {
        return bases;
    }

    public void setBases(Collection<SeaTableBase> bases) {
        this.bases = bases;
    }
}
