package net.iubris.optimus_saint.model.saint.data.value.description;

import net.iubris.optimus_saint.model.saint.data.LocalizedAdapter;

public class DescriptionAdapter extends LocalizedAdapter<Description> {
	public DescriptionAdapter() {
		super(Description.class);
	}
	
	/*@Override
	public Description adaptFromJson(JsonObject jsonObject) throws InstantiationException, IllegalAccessException {
		Description description = super.adaptFromJson(jsonObject);
		System.out.println(description.value);
		return description;
	}*/
}
