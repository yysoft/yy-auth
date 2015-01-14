package com.zz91.util.db.pool;

import javax.sql.DataSource;

public class DBPool {
	private DataSource ds_pooled;

	protected DBPool() {
	}

	public DataSource getDs_pooled() {
		return ds_pooled;
	}

	public void setDs_pooled(DataSource ds_pooled) {
		this.ds_pooled = ds_pooled;
	}
}
