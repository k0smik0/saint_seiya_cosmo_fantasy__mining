package net.iubris.optimus_saint.model.saint.data;

public class Type extends AbstractValue {

	/*@Override
	StatsValue findByInternalEnum(JsonObject jsonObject, String what, String localization) {
		return TypeEnum.valueOf(jsonObject.getJsonObject(what).getString(localization).toUpperCase());
	}
	
	private static enum TypeEnum implements StatsValue {
		VITALITY {
			@Override
			public String getLocalized(Localization localization) {
				return _vitalityMap.get(localization);
			}
		},
		;
		private static Map<Localization,String> _vitalityMap = new HashMap<>();
		static {
			_vitalityMap.put(Localization.EN, "Vitality");
		}
	}*/

}
