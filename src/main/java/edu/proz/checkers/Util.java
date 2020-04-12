package edu.proz.checkers;

import java.nio.ByteBuffer;

public final class Util {

	
	public static String bytes_to_string( ByteBuffer bb ) {
		byte[] b = new byte[bb.limit()];
		bb.get(b);
		return new String(b);
	}
}
