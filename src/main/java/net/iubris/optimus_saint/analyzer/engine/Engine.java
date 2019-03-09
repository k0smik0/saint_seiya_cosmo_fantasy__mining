package net.iubris.optimus_saint.analyzer.engine;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.iubris.optimus_saint.analyzer.model.Saint;
import net.iubris.optimus_saint.analyzer.model.Shop;
import net.iubris.optimus_saint.analyzer.model.equipment.EquipmentItem;
import net.iubris.optimus_saint.analyzer.model.equipment.Item;
import net.iubris.optimus_saint.analyzer.silos.SiloBluePrint;
import net.iubris.optimus_saint.analyzer.silos.SiloEquipment;

public class Engine {

	public static void calculateItemsToShop(List<Saint> saints, Set<Shop> shops) {
		Map<Item, Set<Shop>> toShop = new HashMap<>();
		saints
		.stream()
		.sorted(levelComparatorDescending)
		.forEach(saint->
			saint
			.getEquipmentItemsOnActualEvolutionLevel()
			.stream()
			.filter(e->isEquipmentItemAdmittableAndNotAvailable(e, saint))
			.forEach(e-> {
				shops
				.stream()
				.filter(s->s.getItems().contains(e))
				.forEach(s-> {
					Set<Shop> sets = toShop.get(e);
					if (sets!=null) {
						sets = new HashSet<>();
						toShop.put(e, sets);							
					}
					sets.add(s);
				});
			})
		);		
	}
	private static final Comparator<? super Saint> levelComparatorDescending = new Comparator<Saint>() {
		@Override
		public int compare(Saint o1, Saint o2) {
			int o1Level = o1.getLevel();
			int o2Level = o2.getLevel();
			if (o2Level < o1Level) {
				return -1;
			}
			if (o2Level > o1Level) {
				return 1;
			}
			return 0;
		}
	};
	private final static boolean isEquipmentItemAdmittableAndNotAvailable(EquipmentItem equipmentItem, Saint saint) {
		return equipmentItem.getMinimumLevel()>=saint.getLevel() && !equipmentItem.isAvailable();
	}
	
	public static void calculateItemsToUse(List<Saint> saints, SiloEquipment siloEquipment, SiloBluePrint siloBluePrint) {
//		Predicate<Saint> p = Saint::getEquipmentItemsOnActualEvolutionL
		/*saints
		.stream()
		.sorted(levelComparatorDescending)
		.forEach(saint->
		);*/
	}
	
}
