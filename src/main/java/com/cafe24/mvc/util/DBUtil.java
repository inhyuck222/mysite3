package com.cafe24.mvc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {

	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			// 1. 드라이버 로딩
			Class.forName("com.mysql.jdbc.Driver");

			// 2. 연결하기
			String url = "jdbc:mysql://localhost/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return conn;
	}
	
	public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
		if (conn != null && conn.isClosed() == false) {
			conn.close();
		}
		if (pstmt != null && pstmt.isClosed() == false) {
			pstmt.close();
		}
		if (rs != null && rs.isClosed() == false) {
			rs.close();
		}
	}
	
	public static void close(Connection conn, PreparedStatement pstmt) throws SQLException {
		if (conn != null && conn.isClosed() == false) {
			conn.close();
		}
		if (pstmt != null && pstmt.isClosed() == false) {
			pstmt.close();
		}
	}
}
