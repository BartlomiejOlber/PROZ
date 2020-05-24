package edu.proz.checkers;

import java.nio.ByteBuffer;

/**
 * Util class containing method(s) used by various classes
 * @author bartlomiej
 *
 */
public final class Util {

	
	/**
	 * @param bb ByteBuffer containing a text written
	 * @return String with the text
	 */
	public static String bytes_to_string( ByteBuffer bb ) {
		byte[] b = new byte[bb.limit()];
		bb.get(b);
		return new String(b);
	}
}
