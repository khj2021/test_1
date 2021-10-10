/**
 * JDBCSearchDemo.java
 * com.hainiu.demo
 * Copyright (c) 2021, 海牛版权所有.
 * @author   潘牛                      
*/

package hainiumysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import utils.JdbcUtil;

/**
 * 需求：实现转账逻辑
 * 
 * @author 潘牛
 * @Date 2021年8月2日
 */
public class JDBCTransferAccountsDemo {
	public static void main(String[] args) throws Exception {
		try (
				// 获取数据库连接
				Connection conn = JdbcUtil.getConn();) {
			String name1 = "小强";
			String name2 = "小亮";
			int money = 100;
			transferAccounts(conn, name1, name2, money);

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * 转账方法
	 * @param conn
	 * @param name1  转出者
	 * @param name2  转入者
	 * @param money 转账金额
	*/
	private static void transferAccounts(Connection conn, String name1, String name2, int money) {

		// 小强-100
		String sql1 = "update account set money=money-? where name=?";
		// 小亮+100
		String sql2 = "update account set money=money+? where name=?";

		try (
				// 小强-100
				PreparedStatement pstmt1 = conn.prepareStatement(sql1);
				// 小亮+100
				PreparedStatement pstmt2 = conn.prepareStatement(sql2);

		) {
			// 小强-100
			pstmt1.setInt(1, money);
			pstmt1.setString(2, name1);
			// 执行SQL语句并获取结果
			int executeUpdate1 = pstmt1.executeUpdate();

			// 抛异常
			int x = 1 / 0;

			// 小亮+100
			pstmt2.setInt(1, 100);
			pstmt2.setString(2, name2);
			// 执行SQL语句并获取结果
			int executeUpdate2 = pstmt2.executeUpdate();

			if (executeUpdate1 > 0 && executeUpdate2 > 0) {
				System.out.println("转账成功");
			}

		} catch (Exception e) {
			System.out.println("修改失败");
			e.printStackTrace();
		}

	}

}
