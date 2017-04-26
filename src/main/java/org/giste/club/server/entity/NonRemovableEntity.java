package org.giste.club.server.entity;

import javax.persistence.Entity;

@Entity
public class NonRemovableEntity extends BaseEntity {

	private static final long serialVersionUID = 2703602042485715754L;
	
	protected boolean enabled;

	public NonRemovableEntity() {
	}

	public NonRemovableEntity(Long id, boolean enabled) {
		super(id);
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
