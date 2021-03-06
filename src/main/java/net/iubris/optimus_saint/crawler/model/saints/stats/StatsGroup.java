package net.iubris.optimus_saint.crawler.model.saints.stats;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import net.iubris.optimus_saint.crawler.model.saints.stats.literal.ActiveTime;
import net.iubris.optimus_saint.crawler.model.saints.stats.literal.ClothKind;
import net.iubris.optimus_saint.crawler.model.saints.stats.literal.PromotionClass;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.Accuracy;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.Aura;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.AuraGrowthRate;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.CosmoCostReduction;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.CosmoRecovery;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.Evasion;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.FuryAttack;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.FuryCritical;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.FuryResistance;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.HPDrain;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.HPRecovery;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.Level;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.MaxHP;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.NullFuryResistance;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.NullPhysicalDefense;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.NumericalStat;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.PhysicalAttack;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.PhysicalCritical;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.PhysicalDefense;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.Power;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.Rarity;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.SilenceResistance;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.TechnicalGrowthRate;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.Technique;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.Vitality;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.VitalityGrowthRate;

public class StatsGroup {
	
	private Set<String> FIELDS_TO_SKIP = new HashSet<>( Arrays.asList("rarity","category","promotionClass","clothKind","activeTime","level") ); 
	public int rawSum() {
		int sum = 0;
		Field[] fields = getClass().getFields();
		for (Field field : fields) {
			if ( FIELDS_TO_SKIP.contains(field.getName()) ) {
				continue;
			}
			try {
				NumericalStat numericalStat = (NumericalStat) field.get(this);
				sum+=numericalStat.max;
			} catch (IllegalArgumentException e) {
				System.err.println(e.getMessage());
			} catch (IllegalAccessException e) {
				System.err.println(e.getMessage());
			}
		}
		return sum;
	}
	
	// numerical, specific
	public Rarity rarity;
	
	public Category category;
	
	// literal
	public PromotionClass promotionClass;
	public ClothKind clothKind;
	public ActiveTime activeTime;
	
	// numerical	
	public AuraGrowthRate auraGrowthRate;
	public TechnicalGrowthRate technicalGrowthRate;
	public VitalityGrowthRate vitalityGrowthRate;
	
	public Accuracy accuracy;
	public Aura aura;
	public CosmoCostReduction cosmoCostReduction;
	public CosmoRecovery cosmoRecovery;
	public Evasion evasion;
	public FuryAttack furyAttack;
	public FuryCritical furyCritical;
	public FuryResistance furyResistance;
	public HPDrain hPDrain;
	public HPRecovery hPRecovery;
	public Level level;
	public MaxHP maxHP;
	public NullFuryResistance nullFuryResistance;
	public NullPhysicalDefense nullPhysicalDefense;
	public PhysicalAttack physicalAttack;
	public PhysicalCritical physicalCritical;
	public PhysicalDefense physicalDefense;
	public Power power;	
	public SilenceResistance silenceResistance;
	public Technique technique;
	public Vitality vitality;
	
	
	@Override
	public String toString() {
	    return ReflectionToStringBuilder.toString(this);
	}
}
