package net.iubris.optimus_saint.crawler.skills;

import java.util.HashMap;
import java.util.Map;

public class SkillsGroup {
	
	private final Map<Integer,Skill> skillsMap = new HashMap<>();
	
	public Skill first = skillsMap.get(1);
	public Skill second = skillsMap.get(2);
	public Skill third = skillsMap.get(3);
	public Skill fourth = skillsMap.get(4);
	public Skill seventhSense = skillsMap.get(7);
	
	public SkillsGroup() {
		System.out.println("do nothing");
	}

	public SkillsGroup(Skill first, Skill second, Skill third, Skill fourth, Skill seventhSense) {
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
		this.seventhSense = seventhSense;
	}
	
	public void setFirst(Skill first) {
		this.first = first;
	}
}
