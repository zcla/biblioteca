package zcla71.dao.seatable.config.def.column.data;

import lombok.Data;

@Data
public class ColumnDefDataDate {
    public enum Format {
        ISO_FORMAT("YYYY-MM-DD"),
        ISO_FORMAT_ACCURATE_TO_MINUTE("YYYY-MM-DD HH:mm"),
        US_FORMAT("M/D/YYYY"),
        US_FORMAT_ACCURATE_TO_MINUTE("M/D/YYYY HH:mm"),
        EUROPEAN_FORMAT("DD/MM/YYYY"),
        EUROPEAN_FORMAT_ACCURATE_TO_MINUTE("DD/MM/YYYY HH:mm"),
        GERMAN_FORMAT("DD.MM.YYYY"),
        GERMAN_FORMAT_ACCURATE_TO_MINUTE("DD.MM.YYYY HH:mm");
    
        public final String label;
    
        private Format(String label) {
            this.label = label;
        }
    };

    private Format format;
}
