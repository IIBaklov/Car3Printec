package com.ivo.cars.exc;

public class CarNotFoundException extends RuntimeException {

	public CarNotFoundException(String id) {
		super("Could not find Car with this Registration Number: " + id+"!");
	}
}
