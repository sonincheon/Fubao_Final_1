package common;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Common {
    final static String ORACLE_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    final static String ORACLE_ID = "SCOTT";
    final static String ORACLE_PW = "TIGER";
    final static String ORACLE_DRV = "oracle.jdbc.driver.OracleDriver";        // 오라클이랑 연결하는것. !

    public static Connection getConnection(){
        Connection conn =null;
        try {
            Class.forName(ORACLE_DRV); // 드라이버 로드
            // 연결 얻기
            conn = DriverManager.getConnection(ORACLE_URL, ORACLE_ID, ORACLE_PW);
        }catch (Exception e){
            System.out.println("xxxxxx");
            e.printStackTrace();

        }
        return conn;
    }


    public static void close(Connection conn) {
        try {
            if(conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void close(Statement stmt) {
        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void close(ResultSet rset) {
        try {
            if(rset != null && !rset.isClosed()) {
                rset.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
