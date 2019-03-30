package net.iubris.optimus_saint.crawler.model.saints.skills;

import org.apache.commons.lang3.StringUtils;

public class SkillsGroup {
	
	public Skill first;
	public Skill second;
	public Skill third;
	public Skill fourth;
	private Skill seventhSense;
	private Skill crusade1;
	private Skill crusade2;
	
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
		seventhSense.name = net.iubris.optimus_saint.common.StringUtils.EMPTY;
	}
	
	public boolean hasCrusade2() {
        return StringUtils.isNotBlank(crusade2.name);
    }
	
	public Skill getCrusade1() {
		return crusade1;
	}
	public void setCrusade1(Skill crusade1) {
		this.crusade1 = crusade1;
	}
	
	public Skill getCrusade2() {
        return crusade2;
    }
    public void setCrusade2(Skill crusade2) {
        this.crusade2 = crusade2;
    }
}
