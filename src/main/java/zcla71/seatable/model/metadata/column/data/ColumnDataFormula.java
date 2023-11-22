package zcla71.seatable.model.metadata.column.data;

import java.util.Collection;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ColumnDataFormula extends ColumnData {
	private String formula;
	private Collection<String> operated_columns;
	private String result_type;
}
