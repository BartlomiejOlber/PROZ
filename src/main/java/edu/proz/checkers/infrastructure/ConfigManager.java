package edu.proz.checkers.infrastructure;

import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigManager {

	private static final int DEFAULT_PORT = 9999;
	private static final String LOOPBACK = "127.0.0.1";
	private ObjectMapper jsonParser;
	
	public ConfigManager() {
		jsonParser = new ObjectMapper();
	}
	
	public ConfigParams load( String filepath ) {
		
		ConfigParams params = null;
		try {

        	InputStream input = getClass().getResourceAsStream(filepath);

        	params = jsonParser.readValue(input, ConfigParams.class);
            return params;
		}catch(Exception e) {
			e.printStackTrace();
			params = new ConfigParams( DEFAULT_PORT, LOOPBACK );
			return params;
		}
		
	}

}
