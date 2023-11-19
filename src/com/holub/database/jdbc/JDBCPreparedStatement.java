package com.holub.database.jdbc;

import com.holub.database.Database;
import com.holub.database.Table;
import com.holub.database.jdbc.adapters.StatementAdapter;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class JDBCPreparedStatement extends JDBCStatement{
    private Database database;
    private List<String> sql_batch = new ArrayList<>();
    private String sqlQuery;

    public JDBCPreparedStatement(Database database, String sql)
    {
        super(database);
        this.database = database;
    	this.sqlQuery = sql;
    }
    // need to write own executeUpdate()

}
