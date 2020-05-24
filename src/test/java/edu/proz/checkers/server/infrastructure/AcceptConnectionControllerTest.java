package edu.proz.checkers.server.infrastructure;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.proz.checkers.infrastructure.ConfigParams;

public class AcceptConnectionControllerTest {

	@Test
	void testInit() {
		
		try {
			String address = new String("127.0.0.1");
		   AcceptConnectionHandler acc = new AcceptConnectionHandler(new ConfigParams( 9999,address ));
		   assertNotNull(acc);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
