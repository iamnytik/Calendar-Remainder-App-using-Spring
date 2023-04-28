package com.example.services;

import javax.sql.DataSource;

public interface dbConnection {
	DataSource dataSource = ConfigureDataSource.source();
}
