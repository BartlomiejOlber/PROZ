package edu.proz.checkers.client.infrastructure;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.jupiter.api.Test;

import edu.proz.checkers.infrastructure.ConfigParams;
import edu.proz.checkers.infrastructure.Request;
import edu.proz.checkers.infrastructure.Response;

public class ConnectionControllerTest {

	@Test
	void testInit() {
		BlockingQueue<Request> requestQueue = new LinkedBlockingQueue<Request>( 8 );
		BlockingQueue<Response> responseQueue = new LinkedBlockingQueue<Response>( 8 );
		ConfigParams params = new ConfigParams(9999, new String("127.0.0.1"));
		ConnectionController cc = new ConnectionController(requestQueue, responseQueue, params);
		assertNotNull(cc);
		
	}

}
