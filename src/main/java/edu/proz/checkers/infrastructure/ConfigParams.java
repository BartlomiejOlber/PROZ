package edu.proz.checkers.infrastructure;

/**
 * Data transfer object containing INet address and port; includes only primitive getters and setters
 * @author bartlomiej
 *
 */
public class ConfigParams {

	private int port;
	private String address;
	
	public ConfigParams(int port, String address) {
		this.port = port;
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ConfigParams() {
	
	}

}
