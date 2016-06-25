package com.aliyun.yun.plugin.database;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.aliyun.yun.plugin.database.message.request.YunCountReq;
import com.aliyun.yun.plugin.database.message.request.YunExcuteReq;
import com.aliyun.yun.plugin.database.message.request.YunQueryReq;
import com.aliyun.yun.plugin.database.message.response.YunCountResp;
import com.aliyun.yun.plugin.database.message.response.YunQueryResp;
import com.aliyun.yuntest.framework.command.YunCommand;
import com.aliyun.yuntest.framework.exception.YunException;
import com.aliyun.yuntest.framework.handler.IncomingMessageHandler;
import com.aliyun.yuntest.framework.interaction.TestInteraction;
import com.aliyun.yuntest.framework.message.YunMessage;
import com.aliyun.yuntest.framework.message.YunSessionMessage;
import com.aliyun.yuntest.framework.plugin.Plugin;

/**
 * @author mahongliang
 */
public class DatabasePlugin implements Plugin {

	private static final Logger logger = LoggerFactory
			.getLogger(DatabasePlugin.class);

	private TestInteraction testInteraction;
	private IncomingMessageHandler incomingMessageHandler;
	private JdbcTemplate jdbcTemplate;
	private DataSource dataSource;

	public void doExecute(String sql) {
		this.jdbcTemplate.execute(sql);
	}

	@Override
	public void execute(YunCommand command) {
		// TODO Auto-generated method stub

	}

	public int getCount(String sql) {
		return this.jdbcTemplate.queryForInt(sql);
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public List<Map<String, Object>> getList(String sql) {
		return this.jdbcTemplate.queryForList(sql);
	}

	@Override
	public YunSessionMessage receive() {
		return incomingMessageHandler.receive();
	}

	@Override
	public YunSessionMessage receive(YunMessage message) {
		if (message == null) {
			throw new AssertionError(
					"YunMessage DatabasePlugin received is null!");
		}

		return incomingMessageHandler.receive(message);
	}

	@Override
	public void send(YunMessage message) {
		send(message, null);
	}

	@Override
	public void send(YunMessage message, Object sessionToken) {
		if (message instanceof YunCountReq) {
			logger.info("------> DatabasePlugin sends YunCountReq ------>");
			YunCountReq countReq = (YunCountReq) message;
			YunCountResp resp = new YunCountResp();
			resp.setCount(getCount(countReq.getSql()));
			logger.info("<------ DatabasePlugin recevied YunCountResp <------");
			YunSessionMessage sessionMessage = new YunSessionMessage(resp,
					dataSource);
			incomingMessageHandler.incomingMessage(sessionMessage);
			return;
		}

		if (message instanceof YunQueryReq) {
			logger.info("------> DatabasePlugin sends YunQueryReq ------>");
			YunQueryReq queryReq = (YunQueryReq) message;
			YunQueryResp resp = new YunQueryResp();
			resp.setList(getList(queryReq.getSql()));
			logger.info("<------ DatabasePlugin recevied YunQueryResp <------");
			YunSessionMessage sessionMessage = new YunSessionMessage(resp,
					dataSource);
			incomingMessageHandler.incomingMessage(sessionMessage);
			return;
		}

		if (message instanceof YunExcuteReq) {
			logger.info("------> DatabasePlugin sends YunExcuteReq ------>");
			YunExcuteReq excuteReq = (YunExcuteReq) message;
			this.doExecute(excuteReq.getSql());
			return;
		}

		throw new YunException("DatabasePlugin can send DatabaseMessage only!");

	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void setIncomingMessageHandler(IncomingMessageHandler handler) {
		this.incomingMessageHandler = handler;
	}

	@Override
	public void setTestInteraction(TestInteraction testInteraction) {
		this.testInteraction = testInteraction;
	}

	@Override
	public void start() {
		incomingMessageHandler.setTestInteraction(testInteraction);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
