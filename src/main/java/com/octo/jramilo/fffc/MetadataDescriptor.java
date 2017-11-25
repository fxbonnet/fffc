package com.octo.jramilo.fffc;

import java.util.ArrayList;
import java.util.List;

import com.octo.jramilo.fffc.model.Metadata;

public class MetadataDescriptor {
	private ArrayList<Metadata> metadataList;
	
	public MetadataDescriptor() {
		metadataList = new ArrayList<Metadata>();
	}

	public List<Metadata> getMetadataList() {
		return metadataList;
	}

	public void add(Metadata metadata) {
		metadataList.add(metadata);
	}
}
