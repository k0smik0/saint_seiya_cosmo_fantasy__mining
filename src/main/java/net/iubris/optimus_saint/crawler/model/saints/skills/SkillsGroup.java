package net.iubris.optimus_saint.crawler.model.saints.skills;

import org.apache.commons.lang3.StringUtils;

public class SkillsGroup {
	
	public Skill first;
	public Skill second;
	public Skill third;
	public Skill fourth;
	private Skill seventhSense;
	private Skill crusade;
	
	public SkillsGroup() {
//		System.out.println("do nothing");
	}

	public boolean hasSeventhSense() {
		return !StringUtils.isBlank(seventhSense.description);
	}
	
	public Skill getSeventhSense() {
		return seventhSense;
	}

	public void setSeventhSense(Skill t) {
		seventhSense = t;
	}
	
	public boolean hasCrusade() {
		return StringUtils.isNotBlank(crusade.name);
	}
	
	public Skill getCrusade() {
		return crusade;
	}
	public void setCrusade(Skill crusade) {
		this.crusade = crusade;
	}
}
