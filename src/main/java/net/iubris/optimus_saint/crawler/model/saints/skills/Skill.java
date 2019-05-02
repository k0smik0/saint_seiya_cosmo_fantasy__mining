package net.iubris.optimus_saint.crawler.model.saints.skills;

import java.util.ArrayList;
import java.util.List;

import net.iubris.optimus_saint.common.StringUtils;
import net.iubris.optimus_saint.crawler.model.IddableDefinition;

public class Skill extends IddableDefinition implements Comparable<Skill> {
//	Name name;
//	Description description;
    
    public Skill() {}
    
    public Skill(String name) {
        this.name = name;
    }
	
	String name = StringUtils.EMPTY;
	String shortName = name;
	public String description;
	public List<String> effects = new ArrayList<>();
	public String imageSmall;
	
	public String descriptionIT;
	
    @Override
    public int compareTo(Skill o) {
        return name.compareTo(o.name);
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
            this.shortName = name;
        }
    }

    public String getName() {
        return name;
    }
    
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
    public String getShortName() {
        return shortName;
    }
    
    public boolean isExistant() {
        return !name.isEmpty(); 
//        !StringUtils.UNDERSCORE.equalsIgnoreCase(shortName) || !StringUtils.UNDERSCORE.equalsIgnoreCase(name);
    }

    public boolean hasShortName() {
        return !shortName.isEmpty();
//        !StringUtils.EMPTY.equalsIgnoreCase(shortName) && !StringUtils.UNDERSCORE.equalsIgnoreCase(shortName);
    }
}
