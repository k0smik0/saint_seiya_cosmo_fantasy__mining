package net.iubris.optimus_saint.model.saint.data.promote;

import java.util.HashSet;
import java.util.Set;

import net.iubris.optimus_saint.model.saint.data.value.name.Name;

public class Promote extends Name {

	public int id;
	public int level;
	public Set<Integer> promotingItemIds = new HashSet<>();

}
