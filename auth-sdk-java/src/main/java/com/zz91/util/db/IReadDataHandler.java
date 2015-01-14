package com.zz91.util.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IReadDataHandler {

	public abstract void handleRead(ResultSet paramResultSet)
			throws SQLException;
}
