package net.iubris.optimus_saint.crawler.model.saints.skills;

import java.util.ArrayList;
import java.util.List;

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
		// seventhSense name is pleonastic, so make empty
		seventhSense.name = net.iubris.optimus_saint.common.StringUtils.EMPTY;
	}
	
	public boolean hasCrusade2() {
	    return crusade2.isExistant();
//        return StringUtils.isNotBlank(crusade2.name);
    }
	
	public Skill getCrusade1() {
		return crusade1;
	}
	public void setCrusade1(Skill crusade1) {
		this.crusade1 = crusade1;
		this.crusade1.name = this.crusade1.name.replace("Ⅳ", "IV");
	}
	
	public Skill getCrusade2() {
        return crusade2;
    }
    public void setCrusade2(Skill crusade2) {
        this.crusade2 = crusade2;
    }
    
    public List<Skill> getAllCrusade() {
        List<Skill> l = new ArrayList<Skill>();
        l.add( crusade1 );
        if (hasCrusade2()) {
            l.add( crusade2 );    
        }
        return l;
    }
}
