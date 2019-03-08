package net.iubris.optimus_saint.model.saint.data;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import net.iubris.optimus_saint.model.saint.data.value.type.Type;
import net.iubris.optimus_saint.utils.StringUtils;

public class TypeDeserializer implements JsonbDeserializer<Type> {

	@Override
	public Type deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, java.lang.reflect.Type refletionType) {

		Type type = new Type();
		type.value = StringUtils.EMPTY;
		
		while (jsonParser.hasNext()) {
			Event event = jsonParser.next();
			if (event == JsonParser.Event.KEY_NAME) {
				JsonValue value = jsonParser.getValue();
				String string = jsonParser.getString();
				if (LocalizationUtils.isLocalization(string)) {
					if (LocalizationUtils.getLocalization().name().equalsIgnoreCase(string)) {
						jsonParser.next();
						String s = jsonParser.getString();
//						System.out.println("KEY_NAME TYPE LOCALIZED:"+s);
						type.value = s;
						break;
					}
				} else {
//					System.out.println("KEY_NAME:"+string);
					if ("type".equals(string)) {
//						System.out.println("KEY_NAME TYPE:"+string);
						type.value = string;
						break;
					}
				}
			}
			else if (event == JsonParser.Event.VALUE_STRING) {
				String string = jsonParser.getString();
//				System.out.println("VALUE_STRING: " +string);
				if ("type".equals(string)) {
//					System.out.println("VALUE_STRING TYPE: " +string);
					type.value = string;
					break;
				}
			}
			else if (event == JsonParser.Event.START_OBJECT) {
				JsonObject object = jsonParser.getObject();
//				System.out.println("START_OBJECT: " +object);
				/*if (object!=null) {
					if (object.keySet().contains(LocalizationUtils.getLocalization().name())) {
						String localizedValue = LocalizationUtils.getLocalizedValue(object);
						type.value = localizedValue;
						break;
					}
				} else {
					type.value = StringUtils.EMPTY;
					break;
				}*/
				
			} else if (event == JsonParser.Event.START_ARRAY) {
				JsonArray object = jsonParser.getArray();
//				System.out.println("START_ARRAY: " +object);
			}
			continue;
			
		}
		System.out.println(type.value);
		System.out.println("");
		return type;
	}

}
