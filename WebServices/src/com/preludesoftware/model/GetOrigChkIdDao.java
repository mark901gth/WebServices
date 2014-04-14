package com.preludesoftware.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * Web service class for /GetOrigChkID
 * <p>
 * This class contains the methods that are used by the web service classes in the "com.preludesoftware.web" package.
 * </p>
 * 
 * @author Mark Levine
 * @version 0.2 test - &copy; Prelude Software, 2014.
 */

public class GetOrigChkIdDao extends NamedParameterJdbcDaoSupport //JdbcDaoSupport //NamedParameterJdbcDaoSupport
{
    private String sql = "SELECT OrigID,ID from Chk WHERE OrigID = :OrigID";


    /**
     * This constructor is called by Spring.  For debugging purposes prints a message to stdout.
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
     * @return List of com.preludesoftware.model.CheckRecord
     */
    public List<CheckRecord> get_Chk_ID_by_OrigID_asList(String OrigID)
    {
        System.out.println( "get_Chk_ID_by_OrigID_asList: " + sql );
        return getNamedParameterJdbcTemplate().query( sql, mapParameters( OrigID ), new CheckRecordMapper() );
    }


    /**
     * <p>
     * This method queries the database for Chk records that have the OrigID field thats matches the value of the input
     * parameter OrigID
     * </p>
     * 
     * @param OrigID
     *            Original ID
     * @return org.springframework.jdbc.support.rowset.SqlRowSet
     */
    public SqlRowSet get_Chk_ID_by_OrigID_asSqlRowSet(String OrigID)
    {
        System.out.println( "get_Chk_ID_by_OrigID_asSqlRowSet: " + sql );
        return getNamedParameterJdbcTemplate().queryForRowSet( sql, mapParameters( OrigID ) );
    }

    
    /**
     * <p>
     * Extracts the OrigID and ID fields from the ResultSet and builds a new
     * checkRecord object which is returned.
     * </p>
     */
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


    /**
     * <p>
     * Utility routine which sets the parameter mapping for the SQL statement
     * </p>
     * @param OrigID Original ID
     * @return org.springframework.jdbc.core.namedparam.MapSqlParameterSource
     */
    private MapSqlParameterSource mapParameters(String OrigID)
    {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue( "OrigID", OrigID );
        
        return mapSqlParameterSource;
    }

}
