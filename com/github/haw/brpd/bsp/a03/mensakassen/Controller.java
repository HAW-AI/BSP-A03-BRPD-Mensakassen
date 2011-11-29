package com.github.haw.brpd.bsp.a03.mensakassen;

import com.github.haw.brpd.bsp.a03.mensakassen.implementations.Mensa;
import com.github.haw.brpd.bsp.a03.mensakassen.implementations.Student;

class Controller {
	static final int NUMBER_OF_STUDENTS = 10;
	static final int NUMBER_OF_CASHPOINTS = 3;
	
	public static void main(String[] args) {
		System.out.println("Creating objects...");
		Mensa mensa = new Mensa(NUMBER_OF_CASHPOINTS);
		
		for (int i = NUMBER_OF_STUDENTS; i > 0; i--) {
			mensa.add(Student.newinstance(mensa));
		}
		
		System.out.println("Starting simulation...");
		mensa.start();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("enough waiting....................................................................");
		mensa.interrupt();
	}
}