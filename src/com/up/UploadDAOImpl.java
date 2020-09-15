package com.up;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;



public class UploadDAOImpl implements UploadDAO {

	
	public int insertUpload(Map<String, String> upload) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/xe","test","test");
			String sql = "insert into upload(UP_NUM, org_file_name1, file_path1, org_file_name2, file_path2, up_name) values(seq_up_num.nextval, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setNString(1, upload.get("org_file_path1"));
			ps.setNString(2, upload.get("file_path1"));
			ps.setNString(3, upload.get("org_file_path2"));
			ps.setNString(4, upload.get("file_path2"));
			ps.setNString(5, upload.get("up_name"));
			return ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}
