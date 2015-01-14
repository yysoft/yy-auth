package com.zz91.util.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface IParamReadDataHandler extends IReadDataHandler {

	 public void setParams(PreparedStatement paramPreparedStatement)
	    throws SQLException;
}
