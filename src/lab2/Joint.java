package lab2;

/**
 * Little Wall Rock Climbing 
 * Copyright 2009 Eric McCreath
 * GNU LGPL
 */

public class Joint {
	Spring s1;
	Spring s2;
	double mina, maxa;
	public Joint(Spring s1, Spring s2, double mina, double maxa) {	
		this.s1 = s1;
		this.s2 = s2;
		this.mina = mina;
		this.maxa = maxa;
	}
}
