package sistem.model.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	
	private static Connection conn = null;
    private static boolean testMode = false;

    public static void enableTestMode() { testMode = true; }
    public static void disableTestMode() { testMode = false; }
	
	public static Connection getConnection() {
		if(conn == null) {
			try {

                if (!testMode) {
                    Properties props = loadProperties("PRD");			//=> Carrega os dados do arquivo
                    String url = props.getProperty("dburl");		//=> Obtem a URL
                    conn = DriverManager.getConnection(url, props); //=> Realiza a Conexão
                }
                else{
                    Properties props = loadProperties("TST");			//=> Carrega os dados do arquivo
                    String url = props.getProperty("dburl");		//=> Obtem a URL
                    conn = DriverManager.getConnection(url, props); //=> Realiza a Conexão
                }

				ConnectionKeepAlive.startKeepAlive(conn);
			}
			catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		
		//=> Retorna a nova Conexão ou a Conexão já existente
		return conn;
	}
	
	public static void closeConnection(){
		if(conn != null) {
			try {
				conn.close();
			}
			catch(SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	private static Properties loadProperties(String AMB) {
        String DBFile;

        if(AMB != "PRD"){
            DBFile = "db-test.properties";
        }
        else{
            DBFile = "db.properties";
        }

		try (FileInputStream fs = new FileInputStream(DBFile)){
			Properties props = new Properties();
			props.load(fs);
			
			return props;
		}
		catch(IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	//=> Métodos Auxiliares para fechameto de Objetos SQL
	public static void closeStatement(Statement st) {
		if(st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
}
