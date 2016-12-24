package info.malignantshadow.api.database;

/**
 * An exception that occurs when an error occurs in the database
 * 
 * @author MalignantShadow (Caleb Downs)
 *
 */
public class DatabaseException extends RuntimeException {
	
	private static final long serialVersionUID = -2447436866165068560L;
	
	private String _message;
	
	/**
	 * Create a new Exception
	 * 
	 * @param message
	 *            The message
	 */
	public DatabaseException(String message) {
		_message = message;
	}
	
	@Override
	public String getMessage() {
		return _message;
	}
	
}
