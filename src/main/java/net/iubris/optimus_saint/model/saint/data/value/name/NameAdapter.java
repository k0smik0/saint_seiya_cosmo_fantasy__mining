package net.iubris.optimus_saint.model.saint.data.value.name;

import net.iubris.optimus_saint.model.saint.data.LocalizedAdapter;

public class NameAdapter extends LocalizedAdapter<Name> {

	public NameAdapter() {
		super(Name.class);
	}
	
	/*@Override
	public Name adaptFromJson(JsonObject jsonObject) throws InstantiationException, IllegalAccessException {
		Name name = super.adaptFromJson(jsonObject);
		System.out.println(name.value);
		return name;
	}*/

}
