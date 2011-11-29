package com.github.haw.brpd.bsp.a03.mensakassen.implementations;

public class Student extends Thread {
	public static final int CASHPOINT_SEARCH_DELAY = 200; 
	private static int count;
	private int number;
	private enum State { 
		CASHPOINT, EAT, AWAY;
		public State getNext() {
			return values()[(ordinal()+1) % values().length];
		}
	}
	private State state = State.CASHPOINT;
	private Mensa mensa;
	
	public static Student newinstance(Mensa mensa) {
		return new Student(count++, mensa);
	}
	
	private Student(int number, Mensa mensa) {
		this.number = number;
		this.mensa = mensa;
	}
	
	public void run() {
		while (!this.isInterrupted()) {
			switch (state) {
			case CASHPOINT:
				Kasse cashpoint;
				if ((cashpoint = mensa.getFreeCashpoint()) != null) {
					// accuire cashpoint
					try {
						cashpoint.accuire();
					} catch (InterruptedException e1) {
						return;
					}
					// process paymant ...
					try {
						Thread.sleep(Kasse.DEFAULT_PAYMENT_DELAY);
					} catch (InterruptedException e) {
						return;
					} 
					// release cashpoint
					cashpoint.release();
					System.out.println(this + " just paid his food");
				}
				else {
					System.out.println(this + " can't find a free cashpoint");
					try {
						Thread.sleep(CASHPOINT_SEARCH_DELAY);
					} catch (InterruptedException e) {
						return;
					}
					continue;
				}
				break;
			case EAT:
				System.out.println(this + " starts eating");
				sleepRandom();
				break;
			case AWAY:
				System.out.println(this + " is now away");
				sleepRandom();
				break;
			}
			this.state = state.getNext();
		}
	}
	
	private void sleepRandom() {
		try {
			Thread.sleep((long)(Math.random() * 10000));
		} catch (InterruptedException e) {
			this.interrupt();
		}
	}
	
	public String toString() {
		return String.format("Student %d", number);
	}
}
