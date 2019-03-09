package net.iubris.optimus_saint.analyzer.model.equipment;

import java.util.ArrayList;
import java.util.List;

public class EquipmentItem implements Item {

	private final List<EquipmentItemPiece> equipmentItemPiecesToCraft = new ArrayList<>();
	
	private int minimumLevelRequiredToUse;

	private boolean available;
	
	private EquipmentItem(int minimumLevelRequiredToUse) {
		this.minimumLevelRequiredToUse = minimumLevelRequiredToUse;
	}
	
	public List<EquipmentItemPiece> getEquipmentItemPiecesToCraft() {
		return equipmentItemPiecesToCraft;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	public boolean isAvailable() {
		return available;
	}

	public int getMinimumLevel() {
		return minimumLevelRequiredToUse;
	}

}
