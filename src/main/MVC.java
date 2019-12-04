package main;
import java.io.IOException;

import controller.Controller;

public class MVC {
	
	public static void main(String[] args) 
	{
		Controller controller;
		try {
			controller = new Controller();
			controller.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
