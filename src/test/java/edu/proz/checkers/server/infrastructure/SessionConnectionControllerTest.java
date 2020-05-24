package edu.proz.checkers.server.infrastructure;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;


public class SessionConnectionControllerTest {

	@Test
	void testInit() {
		
		try {

			SessionConnectionController scc = new SessionConnectionController();
			assertNotNull(scc);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
