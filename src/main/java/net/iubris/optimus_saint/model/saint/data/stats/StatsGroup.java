package net.iubris.optimus_saint.model.saint.data.stats;

import net.iubris.optimus_saint.model.saint.data.stats.literal.ActiveTime;
import net.iubris.optimus_saint.model.saint.data.stats.literal.ClothKind;
import net.iubris.optimus_saint.model.saint.data.stats.literal.PromotionClass;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.Accuracy;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.Aura;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.AuraGrowthRate;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.CosmoCostReduction;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.CosmoRecovery;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.Evasion;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.FuryAttack;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.FuryCritical;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.FuryResistance;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.HPDrain;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.HPRecovery;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.Level;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.MaxHP;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.NullFuryResistance;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.NullPhysicalDefense;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.PhysicalAttack;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.PhysicalCritical;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.PhysicalDefense;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.Power;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.Rarity;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.SilenceResistance;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.TechnicalGrowthRate;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.Technique;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.Vitality;
import net.iubris.optimus_saint.model.saint.data.stats.numerical.VitalityGrowthRate;

public class StatsGroup {
	
	// numerical, specific
	public Rarity rarity;
	
	public Category category;
	
	// literal
	public PromotionClass promotionClass;
	public ClothKind clothKind;
	public ActiveTime activeTime;
	
	// numerical	
	public Accuracy accuracy;
	public Aura aura;
	public AuraGrowthRate auraGrowthRate;
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
	public TechnicalGrowthRate technicalGrowthRate;
	public Technique technique;
	public Vitality vitality;
	public VitalityGrowthRate vitalityGrowthRate;
	
}
