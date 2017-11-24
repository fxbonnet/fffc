package com.octo.jramilo.fffc.field;

import com.octo.jramilo.fffc.model.MetadataType;

public class FieldFactory {
	
	public static Field getField(final MetadataType type) {
		Field field;
		switch (type) {
			case NUMERIC:
				field = new NumericField();
				break;
			case STRING:
				field = new StringField();
				break;
			case DATE:
				field = new DateField();
				break;
			
			default:
				throw new IllegalArgumentException("No such field!");
		}
		
		return field;
	}
}
