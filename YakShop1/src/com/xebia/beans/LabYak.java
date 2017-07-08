package com.xebia.beans;

import javax.xml.bind.annotation.XmlAttribute;

public class LabYak {

	private String name;
	private double age;
	private String sex;
	
	public String getName() {
		return name;
	}
	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}
	public double getAge() {
		return age;
	}
	@XmlAttribute
	public void setAge(double age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	@XmlAttribute
	public void setSex(String sex) {
		this.sex = sex;
	}
}
