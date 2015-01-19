package cache;

public enum KeyManager {
	ExamplePrepare("example_prepare"),
	ExampleRunning("example_running"),
	ExampleFailed("example_failed"),
	ExampleCommited("example_commited");
	
	private String key;
	private KeyManager(String key){
		this.key = key;
	}
	
	public String getKey() {
		return this.key;
	}
}
