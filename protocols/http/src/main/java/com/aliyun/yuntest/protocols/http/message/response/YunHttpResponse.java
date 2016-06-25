package com.aliyun.yuntest.protocols.http.message.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.StatusLine;

import com.aliyun.yuntest.framework.message.YunMessage;

/**
 * @author mahongliang
 */
public abstract class YunHttpResponse implements YunMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4532737296891455202L;
	protected StatusLine statusLine;
	protected List headerList;

	public void addHeader(Header header) {
		if (headerList == null) {
			headerList = new ArrayList();
		}

		headerList.add(header);
	}

	public List getHeaderList() {
		return headerList;
	}

	public StatusLine getStatusLine() {
		return statusLine;
	}

	public void setHeaderList(List headerList) {
		this.headerList = headerList;
	}

	public void setStatusLine(StatusLine statusLine) {
		this.statusLine = statusLine;
	}
}
