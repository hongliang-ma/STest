package com.dagger.dtest.datadrive;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import au.com.bytecode.opencsv.CSVReader;

public class CsvDataProvider implements IDataProvider {
	public String csvFileName = null;
	CSVReader reader = null;
	public int sum = 0;
	private static final Log log = LogFactory.getLog(CsvDataProvider.class);

	public CsvDataProvider(Method m) {
		csvFileName = getDataFilePath(m);
		Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(csvFileName);
	}

	@Override
	public String getDataFilePath(Method m) {
		return null;
	}

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public Object[] next() {
		return null;
	}

	@Override
	public void remove() {

	}

}
