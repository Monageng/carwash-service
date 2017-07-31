package kbm.co.za.mobile.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import kbm.co.za.mobile.vo.Client;

public class AppConfig extends Application {

	@ApplicationPath("/rest")
	public class ApplicationConfig extends Application {
	    public Set<Class<?>> getClasses() {
	    	System.out.println("AppConfig");
	        return new HashSet(Arrays.asList(Client.class, ClientService.class));
	    }
	}
}
