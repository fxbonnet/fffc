package com.octo.jramilo.fffc.model;

public enum MetadataType {
	STRING("string"),
	NUMERIC("numeric"),
	DATE("date"),
	UNKNOWN("unknown");
	
	private String metaName;
	
	MetadataType(final String metaName) {
		this.metaName = metaName;
	}
	
	public static MetadataType getMetaType(final String name) {
		for(MetadataType type : MetadataType.values()) {
			if(type.metaName.equalsIgnoreCase(name)) {
				return type;
			}
		}
		return UNKNOWN;
	}
}
