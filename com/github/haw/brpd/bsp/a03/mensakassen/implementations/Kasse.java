package com.github.haw.brpd.bsp.a03.mensakassen.implementations;

import java.util.concurrent.Semaphore;

public class Kasse {
	public static final int DEFAULT_PAYMENT_DELAY = 500;
	private static int count;
	private int number;
	private Semaphore semaphore;
	
	public Kasse(int number) {
		this.number = number;
		this.semaphore = new Semaphore(1);
	}

	public static Kasse newinstance() {
		return new Kasse(count++);
	}
	
	public String toString() {
		return String.format("Kasse %d", this.number);
	}
	
	public void accuire() throws InterruptedException {
		this.semaphore.acquire();
	}
	
	public void release() {
		this.semaphore.release();
	} 
	
	public boolean isFree() {
		return this.semaphore.availablePermits() > 0;
	}
}
