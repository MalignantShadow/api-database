package info.malignantshadow.api.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Represents a MySQL query result.
 * 
 * @author MalignantShadow (Caleb Downs)
 *
 */
public class QueryResult implements Iterable<QueryResultRow> {
	
	private List<QueryResultRow> _result;
	
	/**
	 * Create a new QueryResult from a {@link ResultSet}
	 * 
	 * @param rs
	 *            The ResultSet
	 */
	public QueryResult(ResultSet rs) {
		try {
			_result = new ArrayList<QueryResultRow>();
			String[] columns = new String[rs.getMetaData().getColumnCount()];
			for (int i = 0; i < columns.length; i++)
				columns[0] = rs.getMetaData().getColumnName(i + 1);
			
			while (rs.next()) {
				Map<String, Object> data = new HashMap<String, Object>();
				for (String s : columns)
					data.put(s, rs.getObject(s));
				_result.add(new QueryResultRow(data));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Get a value from the result. This method does not check whether the index is valid, care should be used to prevent a {@link IndexOutOfBoundsException}
	 * 
	 * @param index
	 *            The index of the row
	 * @param column
	 *            The column name
	 * @return The value
	 * @see QueryResultRow#getValue(String)
	 */
	public Object get(int index, String column) {
		return _result.get(index).get(column);
	}
	
	/**
	 * Get a string from the result. This method does not check whether the index is valid, care should be used to prevent a {@link IndexOutOfBoundsException}
	 * 
	 * @param index
	 *            The index of the row
	 * @param column
	 *            The column name
	 * @return The string
	 * @see QueryResultRow#getString(String)
	 */
	public String getString(int index, String column) {
		return _result.get(index).asString(column);
	}
	
	/**
	 * Get a number from the result. This method does not check whether the index is valid, care should be used to prevent a {@link IndexOutOfBoundsException}
	 * 
	 * @param index
	 *            The index of the row
	 * @param column
	 *            The column name
	 * @return The Number
	 * @see QueryResultRow#getNumber(String)
	 */
	public Number getNumber(int index, String column) {
		return _result.get(index).asNumber(column);
	}
	
	/**
	 * Get a boolean from the result. This method does not check whether the index is valid, care should be used to prevent a {@link IndexOutOfBoundsException}
	 * 
	 * @param index
	 *            The index of the row
	 * @param column
	 *            The column name
	 * @return The value
	 * @see QueryResultRow#getBoolean(String)
	 */
	public boolean getBoolean(int index, String column) {
		return _result.get(index).asBoolean(column);
	}
	
	/**
	 * Get a timestamp from the result. This method does not check whether the index is valid, care should be used to prevent a {@link IndexOutOfBoundsException}
	 * 
	 * @param index
	 *            The index of the row
	 * @param column
	 *            The column name
	 * @return The value
	 * @see QueryResultRow#getTimestamp(String)
	 */
	public Timestamp getTimestamp(int index, String column) {
		return _result.get(index).getTimestamp(column);
	}
	
	/**
	 * Is this result empty?
	 * 
	 * @return {@code true} if this result has now rows
	 */
	public boolean isEmpty() {
		return rows() == 0;
	}
	
	/**
	 * Get the amount of rows within the result.
	 * 
	 * @return The rows
	 */
	public int rows() {
		return _result.size();
	}
	
	@Override
	public Iterator<QueryResultRow> iterator() {
		return _result.iterator();
	}
	
}
