package com.hibernate;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateWebLev {

	public static void main(String[] args) {
		//create session factory
		File f1 = new File("/Users/RobertoBlanco/Desktop/tesst.xslx");
		File f2 = new File("/Users/RobertoBlanco/Desktop/hej.txt");
		SessionFactory factory = new Configuration().
				configure("hibernate.cfg.xml").
				addAnnotatedClass(WebLev.class).
				buildSessionFactory();
		
		/*
		 * Radera alla rader och nollställ auto increment: truncate ArkivIT.webbleveranser
		 */
		
		//Create session
		Session session = factory.getCurrentSession();
		
		try 
		{
			
			//create a webleverans object
			System.out.println("Create a new object");
			WebLev webLev = new WebLev("Falafelkungen" , f1.getAbsoluteFile(), f2.getAbsoluteFile());
			//start a transaction
			session.beginTransaction();
			
			//save the student
			System.out.println("Saving the object...");
			session.save(webLev);
			
			//commit transaction
			session.getTransaction().commit();
			System.out.println("Done!");
		}
		finally
		{
			factory.close();
		}
	}

}
