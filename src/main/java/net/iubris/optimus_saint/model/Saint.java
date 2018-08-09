package net.iubris.optimus_saint.model;

import java.util.ArrayList;
import java.util.List;

import net.iubris.optimus_saint.model.equipment.EquipmentItem;

public class Saint {
 
	private String name;
	private int level;
	private int rarity;
	private EvolutionLevel evolutionLevel;
	
	private final List<EquipmentItem> equipmentItemsAssignedOnActualEvolutionLevel;

	public Saint(String name, int level, int rarity, EvolutionLevel evolutionLevel, List<EquipmentItem> equipmentItemsAssignedOnActualEvolutionLevel) {
		this.name = name;
		this.level = level;
		this.rarity = rarity;
		this.evolutionLevel = evolutionLevel;
		this.equipmentItemsAssignedOnActualEvolutionLevel = equipmentItemsAssignedOnActualEvolutionLevel;
	}
	
	public Saint() {
		this.equipmentItemsAssignedOnActualEvolutionLevel = new ArrayList<>();
	}

	
	
	
	
	
	
	
	
	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final int getLevel() {
		return level;
	}

	public final void setLevel(int level) {
		this.level = level;
	}

	public int getRarity() {
		return rarity;
	}
	
	public void setRarity(int rarity) {
		this.rarity = rarity;
	}

	public final EvolutionLevel getEvolutionLevel() {
		return evolutionLevel;
	}

	public final void setEvolutionLevel(EvolutionLevel evolutionLevel) {
		this.evolutionLevel = evolutionLevel;
	}
	
	public List<EquipmentItem> getEquipmentItemsOnActualEvolutionLevel() {
		return equipmentItemsAssignedOnActualEvolutionLevel;
	}
	
	
}
