package net.iubris.optimus_saint.crawler.model.saints.skills;

import net.iubris.optimus_saint.crawler.model.IddableDefinition;

public class Skill extends IddableDefinition implements Comparable<Skill> {
//	Name name;
//	Description description;
    
    public Skill() {}
    
    public Skill(String name) {
        this.name = name;
    }
	
	public String name;
	public String description;
	public String imageSmall;
	
    @Override
    public int compareTo(Skill o) {
        return name.compareTo(o.name);
    }
}
