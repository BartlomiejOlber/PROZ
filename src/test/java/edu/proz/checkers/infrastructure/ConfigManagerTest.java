package edu.proz.checkers.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;


public class ConfigManagerTest {

	@Test
	void testCase() {
		ConfigManager cm = new ConfigManager();
		ConfigParams paramsClient = cm.load("client_params.json");
		ConfigParams paramsServer = cm.load("incorrect");
		assertEquals( 9999, paramsClient.getPort() );
		assertEquals( "127.0.0.1", paramsClient.getAddress());
		assertEquals( 9999, paramsServer.getPort() );
		assertEquals( "127.0.0.1", paramsServer.getAddress());
	}
}
