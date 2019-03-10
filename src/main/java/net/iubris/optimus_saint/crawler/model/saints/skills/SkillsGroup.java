package net.iubris.optimus_saint.crawler.model.saints.skills;

import org.apache.commons.lang3.StringUtils;

public class SkillsGroup {
	
	/*private final Map<Integer,Skill> skillsMap = new HashMap<>();
	
	public Skill first = skillsMap.get(1);
	public Skill second = skillsMap.get(2);
	public Skill third = skillsMap.get(3);
	public Skill fourth = skillsMap.get(4);
	private Skill seventhSense = skillsMap.get(7);*/
	
	public Skill first;
	public Skill second;
	public Skill third;
	public Skill fourth;
	private Skill seventhSense;
	
	public SkillsGroup() {
//		System.out.println("do nothing");
	}

	/*public SkillsGroup(Skill first, Skill second, Skill third, Skill fourth, Skill seventhSense) {
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
		this.seventhSense = seventhSense;
	}*/
	
	public boolean hasSeventhSense() {
		return StringUtils.isNotBlank(seventhSense.name);
	}
	
	public Skill getSeventhSense() {
		return seventhSense;
	}

	public void setSeventhSense(Skill t) {
		seventhSense = t;
	}
	
	/*public void setFirst(Skill first) {
		this.first = first;
	}*/
}
