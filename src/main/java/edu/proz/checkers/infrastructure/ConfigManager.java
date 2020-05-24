package edu.proz.checkers.infrastructure;

import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class responsible for loading json configuration file to data transfer object
 * @author bartlomiej
 *
 */
public class ConfigManager {

	private static final int DEFAULT_PORT = 9999;
	private static final String LOOPBACK = "127.0.0.1";
	private ObjectMapper jsonParser;
	
	/**
	 * initializes json ObjectMapper
	 */
	public ConfigManager() {
		jsonParser = new ObjectMapper();
	}
	
	/**
	 * Loads the configuration parameters from a json file; in case that the provided filepath is invalid returns loopback 
	 * address and port number 9999 
	 * @param filepath string containing path to the file with configuration parameters
	 * @return DTO with INet address and port
	 */
	public ConfigParams load( String filepath ) {
		
		ConfigParams params = null;
		try {

        	InputStream input = getClass().getResourceAsStream(filepath);

        	params = jsonParser.readValue(input, ConfigParams.class);
            return params;
		}catch(Exception e) {
		//	e.printStackTrace();
			params = new ConfigParams( DEFAULT_PORT, LOOPBACK );
			return params;
		}
		
	}

}
