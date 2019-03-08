package net.iubris.optimus_saint.crawler.promote;

import java.util.HashSet;
import java.util.Set;

import net.iubris.optimus_saint.crawler.NameableDefinition;

public class Promote extends NameableDefinition {
	public int level;
	public Set<Integer> promotingItemIds = new HashSet<>();
}
