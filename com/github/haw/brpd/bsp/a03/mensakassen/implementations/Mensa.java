package com.github.haw.brpd.bsp.a03.mensakassen.implementations;

import java.util.ArrayList;
import java.util.List;

public class Mensa extends Thread {
	public static final int DEFAULT_NUMBER_OF_CASHPOINTS = 3;
	private List<Kasse> cashpoints;
	private List<Student> students;
	
	public Mensa() {
		this(DEFAULT_NUMBER_OF_CASHPOINTS);
	}
	public Mensa(int number_of_cashpoints) {
		cashpoints = new ArrayList<Kasse>(number_of_cashpoints);
		for (int i = number_of_cashpoints; i > 0; i--) {
			cashpoints.add(Kasse.newinstance());
		}
		this.students = new ArrayList<Student>();
	}
	
	public void add(Student student) {
		this.students.add(student);
	}
	
	public void start() {
		for (Student s : students) {
			s.start();
		}
		super.start();
	}
	public void interrupt() {
		synchronized (this) {
			for (Student s : students) {
				s.interrupt();
			}
			super.interrupt();
		}
	}
	
	public Kasse getFreeCashpoint() {
		for (Kasse cashpoint : cashpoints) {
			if (cashpoint.isFree()) {
				return cashpoint;
			}
		}
		return null;
	}
}
