package info.malignantshadow.api.database;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Represents a row in a database query result.
 * 
 * @author MalignantShadow (Caleb Downs)
 *
 */
public class QueryResultRow {
	
	private Map<String, Object> _data;
	
	/**
	 * Construct a new row using the given map. The keys represent the column name.
	 * 
	 * @param data
	 *            The data
	 */
	public QueryResultRow(Map<String, Object> data) {
		_data = data;
	}
	
	/**
	 * Does this row contain a column with the given.
	 * 
	 * @param column
	 *            The column name
	 * @return {@code true} if this row has the column {@code column}.
	 */
	public boolean hasColumn(String column) {
		return _data.containsKey(column);
	}
	
	/**
	 * Get a value from this row
	 * 
	 * @param column
	 *            The column name
	 * @return The value.
	 */
	public Object get(String column) {
		return _data.get(column);
	}
	
	/**
	 * Get a value from this row as a string. This method will never return null. If the value at the given column does not exist or is {@code null}, this method
	 * will return {@code "null"}. Otherwise, the method returns the {@code .toString()} value of the object.
	 * 
	 * @param column
	 *            the name of the column
	 * @return The value as a string.
	 */
	public String asString(String column) {
		Object o = get(column);
		if (o == null)
			return "null";
		
		return o.toString();
	}
	
	/**
	 * Is the value at the given column a number?
	 * 
	 * @param column
	 *            The column name
	 * @return {@code true} if the value is an instance of {@link Number}.
	 */
	public boolean isNumber(String column) {
		return get(column) instanceof Number;
	}
	
	/**
	 * Get a value as a number. This method is shorthand for {@code (Number) get(column)}
	 * 
	 * @param column
	 *            The column name
	 * @return The value as a number
	 */
	public Number asNumber(String column) {
		return (Number) get(column);
	}
	
	/**
	 * Get a value as a boolean. This method will return {@code true} if the value at {@code column} is if any of the following is true:
	 * <ul>
	 * <li>The value is and instance of Boolean and is {@code true}</li>
	 * <li>The value is a Number and equal to exactly 1</li>
	 * <li>The value is a String and is equal to {@code "1"} or {@code "true"} (case-insensitive)</li>
	 * </ul>
	 * Otherwise, this method returns {@code false}.
	 * 
	 * @param column
	 *            The column name
	 * @return The value as a boolean.
	 */
	public boolean asBoolean(String column) {
		Object o = get(column);
		if (o instanceof Boolean)
			return (Boolean) o;
		else if (o instanceof Number)
			return ((Number) o).byteValue() == 1;
		else if (o instanceof String) {
			String s = (String) o;
			return s.equals("1") || s.equalsIgnoreCase("true");
		}
		
		return false;
	}
	
	/**
	 * Get a value as a Timestamp.
	 * <ul>
	 * <li>If the value at {@code column} is a Timestamp object, then it is casted and returned.</li>
	 * <li>If the value is a Number, then a Timestamp object is returned using its long value.</li>
	 * <li>If the value is a String, then Timestamp.valueof(value) is returned. In this case, the method will throw an exception if
	 * the value does not have the correct format</li>
	 * </ul>
	 * 
	 * @param column
	 * @return
	 */
	public Timestamp getTimestamp(String column) {
		Object o = get(column);
		if (o instanceof Number)
			return new Timestamp(((Number) o).longValue());
		else if (o instanceof Timestamp)
			return (Timestamp) o;
		else if (o instanceof String)
			return Timestamp.valueOf((String) o);
		
		return null;
	}
	
}
