package com.zz91.util.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface IInsertUpdateHandler {

	public abstract void handleInsertUpdate(PreparedStatement paramPreparedStatement)
    throws SQLException;
}
