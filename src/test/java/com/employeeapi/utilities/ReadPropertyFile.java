package com.employeeapi.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertyFile {

	Properties properties;

	public ReadPropertyFile(String FilePath) throws IOException {

		try {
			FileInputStream Locator = new FileInputStream(FilePath);
			properties = new Properties();
			properties.load(Locator);
		} catch (IOException e) {
			e.getMessage();
		}

	}

	public String getData(String ElementName) throws Exception {
		// Read value using the logical name as Key
		String data = properties.getProperty(ElementName);
		return data;
	}

	
}
