package net.iubris.optimus_saint.model;

import java.util.HashSet;
import java.util.Set;

import net.iubris.optimus_saint.model.equipment.Item;

public class Shop {

	private Type type;
	private Set<Item> items = new HashSet<>();
	
	public Type getType() {
		return type;
	}
	
	public Set<Item> getItems() {
		return items;
	}
	
	public static enum Type {
		Normal,
		Rare,
		Premium,
		Cosmo,
		Guild,
		Crusade;
	}
}
