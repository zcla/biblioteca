package zcla71.seatable.model.metadata.column;

import lombok.Data;
import lombok.EqualsAndHashCode;
import zcla71.seatable.model.metadata.column.data.ColumnDataFormula;

@Data
@EqualsAndHashCode(callSuper=true)
public class ColumnFormula extends Column {
	private ColumnDataFormula data;
}
