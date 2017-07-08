package com.xebia.beans;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="herd")
public class Herd {
	
	private ArrayList<LabYak> labYaks;

	public ArrayList<LabYak> getLabYaks() {
		return labYaks;
	}
	
	@XmlElement (name ="labyak")
	public void setLabYaks(ArrayList<LabYak> labYaks) {
		this.labYaks = labYaks;
	}


}
