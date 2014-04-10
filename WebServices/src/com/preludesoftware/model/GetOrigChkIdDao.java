package com.preludesoftware.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * (c) Prelude Software - 2014 <br>
 * <p>
 * This class contains the methods that are used by the web service classes in the "com.preludesoftware.web" package.
 * </p>
 * 
 * @author Mark Levine
 * @version 0.1 test
 */

public class GetOrigChkIdDao extends NamedParameterJdbcDaoSupport //JdbcDaoSupport //NamedParameterJdbcDaoSupport
{

    /**
     * This constructor is used only for debugging. Prints a message to stdout.
     */
    public GetOrigChkIdDao()
    {
        System.out.println( " # " + this.getClass().getName() + ".constructor noparms" );
    }


    /**
     * @param jdbcTemplate
     *            which was set by Spring using the setDataSource method.
     */
    public GetOrigChkIdDao(JdbcTemplate jdbcTemplate)
    {
        System.out.println( " # " + this.getClass().getName() + ".constructor ( jdbcTemplate )" );
        this.setJdbcTemplate( jdbcTemplate );
    }


    /**
     * <p>
     * This method queries the database for Chk records that have the OrigID field thats matches the value of the input
     * parameter OrigID
     * </p>
     * 
     * @param OrigID
     *            Original ID
     * @return List of CheckRecord objects
     */
    public List<CheckRecord> getChk_ID_by_OrigID(String OrigID)
    {
        String sql = null;
        SqlParameterSource namedParameters;
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        sql = "SELECT OrigID,ID from Chk WHERE OrigID = :OrigID";
        mapSqlParameterSource.addValue( "OrigID", OrigID );
        namedParameters = mapSqlParameterSource;

        System.out.println( "getChk_ID_by_OrigID: " + sql );
        return getNamedParameterJdbcTemplate().query( sql, namedParameters, new CheckRecordMapper() );
    }

    private static final class CheckRecordMapper implements RowMapper<CheckRecord>
    {
        @Override
        public CheckRecord mapRow(ResultSet resultSet, int rowNum) throws SQLException
        {
            CheckRecord checkRecord = new CheckRecord();

            checkRecord.setOrigID( resultSet.getString( "OrigID" ) );
            checkRecord.setID( resultSet.getString( "ID" ) );

            return checkRecord;
        }
    }

}
