package com.aliyun.yuntest.plugin.database.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.yun.plugin.database.DBManager;
import com.aliyun.yun.plugin.database.utils.DataMap;
import com.aliyun.yuntest.framework.YunTestJunitBase;
import com.aliyun.yuntest.framework.interaction.TestInteraction;
import com.aliyun.yuntest.framework.plugin.Plugin;

public class TestDatabasePlugin extends YunTestJunitBase {

	private static final Logger logger = LoggerFactory
			.getLogger(TestDatabasePlugin.class);

	Plugin dbClient;

	@Override
	public void setUp(TestInteraction testInteraction) {
		logger.info("TestDatabasePlugin setUp");
		dbClient = testInteraction.getPlugin("database");
	}

	@Override
	public void tearDown(TestInteraction testInteraction) {

	}

	@Override
	public void testStart(TestInteraction testInteraction) {

		Connection dbConnnection = DBManager.connectDb("testdb02");

		List<DataMap> dataList = new ArrayList<DataMap>();
		try {
			PreparedStatement stmt = dbConnnection
					.prepareStatement("select * from city");
			ResultSet resultSet = stmt.executeQuery();
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			while (resultSet.next()) {
				DataMap map = new DataMap();
				for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
					map.put(resultSetMetaData.getColumnName(i).toUpperCase(),
							resultSet.getObject(i));
				}
				logger.info("map size = {}", map.size());
				dataList.add(map);
			}

			logger.info("dataList size = {}", dataList.size());

		} catch (SQLException e) {
			logger.error("DB operation error!", e);
		} finally {
			DBManager.releaseConnection(dbConnnection);
		}
	}

}
