package ru.vk.cometa.core;

@SuppressWarnings("serial")
public class ManagedException extends Exception {
	public ManagedException(String message){
		super(message);
	}
	
	
	public ManagedException(String message, Throwable cause){
		super(message, cause);
	}
}
