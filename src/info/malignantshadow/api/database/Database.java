package info.malignantshadow.api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents a MySQL database.
 * 
 * @author MalignantShadow (Caleb Downs)
 *
 */
public class Database {
	
	private Connection _conn;
	private String _sAddress, _dbName, _dbUser, _dbPass;
	private int _sPort;
	
	private static final String CONNECTION_STRING = "jdbc:mysql://%s:%d/%s?user=%s&password=%s";
	
	/**
	 * Create a new Database instance using the given parameters. A connection is not attempted.
	 * 
	 * @param serverAddress
	 *            The server address/host (e.g. {@code localhost} or {@code example.com})
	 * @param serverPort
	 *            The server port (typically 3000 is used)
	 * @param databaseName
	 *            The name of the database
	 * @param databaseUser
	 *            The username
	 * @param databasePass
	 *            The password
	 */
	public Database(String serverAddress, int serverPort, String databaseName, String databaseUser, String databasePass) {
		_sAddress = serverAddress;
		_sPort = serverPort;
		_dbName = databaseName;
		_dbUser = databaseUser;
		_dbPass = databasePass;
	}
	
	/**
	 * Attempt a connection to the database
	 * 
	 * @return {@code true} if the connection was successful.
	 */
	public boolean connect() {
		try {
			if (_conn == null || !_conn.isValid(5))
				_conn = DriverManager.getConnection(String.format(CONNECTION_STRING, _sAddress, _sPort, _dbName, _dbUser, _dbPass));
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private PreparedStatement prepare(String sql, Object[] params) throws SQLException {
		PreparedStatement stmt = _conn.prepareStatement(sql);
		int counter = 1;
		for (Object o : params) {
			if (o instanceof Integer)
				stmt.setInt(counter++, (Integer) o);
			else if (o instanceof Short)
				stmt.setShort(counter++, (Short) o);
			else if (o instanceof Long)
				stmt.setLong(counter++, (Long) o);
			else if (o instanceof Double)
				stmt.setDouble(counter++, (Double) o);
			else if (o instanceof String)
				stmt.setString(counter++, (String) o);
			else if (o == null)
				stmt.setNull(counter++, java.sql.Types.NULL);
			else
				stmt.setObject(counter++, o);
		}
		
		return stmt;
	}
	
	/**
	 * Write to the database. It is preferred that the caller use something similar to {@code INSERT INTO `table` WHERE `value`=? AND `another_value`=?} to help prevent SQL injection.
	 * 
	 * @param sql
	 *            The query string
	 * @param params
	 *            The parameters to the query
	 */
	public void write(String sql, Object... params) {
		try {
			connect();
			PreparedStatement stmt = prepare(sql, params);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write to the database. It is preferred that the caller use something similar to {@code SELECT * FROM `table` WHERE `value`=? AND `another_value`=?} to help prevent SQL injection.
	 * 
	 * @param sql
	 *            The query string
	 * @param params
	 *            The parameters to the query
	 */
	public QueryResult read(String sql, Object... params) {
		connect();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		QueryResult result = null;
		try {
			stmt = prepare(sql, params);
			rs = stmt.executeQuery();
			if (rs != null)
				result = new QueryResult(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return result;
	}
	
}
