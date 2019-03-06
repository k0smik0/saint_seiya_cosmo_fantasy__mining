package net.iubris.optimus_saint.model.saint.data.promote;

import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbTypeAdapter;

import net.iubris.optimus_saint.model.saint.data.NameableDefinition;

public class Item extends NameableDefinition {
	public String quantity;
	public int equipmentLevel;
	public boolean available;
	@JsonbTypeAdapter(MaterialArrayAdapter.class)
	public List<String> materialIds = new ArrayList<>();
}
