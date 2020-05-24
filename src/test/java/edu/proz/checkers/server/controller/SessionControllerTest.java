package edu.proz.checkers.server.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import edu.proz.checkers.infrastructure.GetOpponentEvent;
import edu.proz.checkers.infrastructure.Response;
import edu.proz.checkers.infrastructure.WaitResponse;
import edu.proz.checkers.server.infrastructure.SessionConnectionController;

public class SessionControllerTest {

	@Test
	void initTest() {
		try {
			SessionConnectionController scc = new SessionConnectionController();
			SessionController sc = new SessionController(scc);
			assertNotNull(sc);
		}catch( Exception e ) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	void getResponseTest() {
		try {
			SessionConnectionController scc = new SessionConnectionController();
			SessionController sc = new SessionController(scc);
			assertNotNull(sc);
			Response response = sc.getResponse( new GetOpponentEvent(1));
			assertEquals(WaitResponse.class, response.getClass());
			assertEquals(1, response.getPlayerId() );
		}catch( Exception e ) {
			e.printStackTrace();
		}
		
	}

}
