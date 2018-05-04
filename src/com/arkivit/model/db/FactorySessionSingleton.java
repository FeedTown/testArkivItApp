package com.arkivit.model.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactorySessionSingleton {

	private static SessionFactory factoryInstance = null;

	private FactorySessionSingleton() 
	{

	}

	public static SessionFactory getSessionFactoryInstance()
	{

		if(factoryInstance == null) 
		{
			factoryInstance = new Configuration().
					configure("hibernate.cfg.xml").
					addAnnotatedClass(Webbleveranser.class).
					buildSessionFactory();
		}
		else 
		{
			factoryInstance.close();
		}
		return factoryInstance;
	}
	
	
}


