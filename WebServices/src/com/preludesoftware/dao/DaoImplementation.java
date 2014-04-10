package com.preludesoftware.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * (c) Prelude Software - 2014 <br>
 * <p>
 * This class contains the methods that are used for the DAO implementation. The setXX methods are called by Spring and
 * the getXX methods are used by the other classes and methods in this web service.
 * </p>
 * 
 * @author Mark Levine
 * @version 0.1 test
 */

@Component
public class DaoImplementation
{

    private DataSource                 dataSource;
    private JdbcTemplate               jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    /**
     * This constructor is used only for debugging. Prints a message to stdout.
     */
    public DaoImplementation()
    {
        System.out.println( " # " + this.getClass().getName() + " constructor just prints this message" );
    }


    /**
     *
     * @return dataSource which was set by Spring using the setDataSource method.
     */
    public DataSource getDataSource()
    {
        System.out.println( " < " + this.getClass().getName() + ".getDataSource " + this.dataSource );
        return this.dataSource;
    }


    /**
     * Spring calls this method to set the data source. The data source is defined in the spring.xml file and contains
     * the database connection string.
     * 
     * @param dataSource
     *            object as provided by Spring
     */
    @Autowired
    public void setDataSource(DataSource dataSource)
    {
        System.out.println( " > " + this.getClass().getName() + ".setDataSource" );

        // Data Source is set by Spring
        this.dataSource = dataSource;

        System.out.println( " < " + this.getClass().getName() + ".setDataSource = " + this.dataSource );
    }


    /**
     * 
     * @return jdbcTemplate
     */
    public JdbcTemplate getJdbcTemplate()
    {
        System.out.println( " < " + this.getClass().getName() + ".getJdbcTemplate " + this.jdbcTemplate );
        return this.jdbcTemplate;
    }


    /**
     * Spring calls this method to set the jdbcTemplate object which will be used for all database access.
     * 
     * @param jdbcTemplate
     *            object as provided by Spring.
     */
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
    {
        System.out.println( " > " + this.getClass().getName() + ".setJdbcTemplate" );

        // JDBC Template is set by Spring
        this.jdbcTemplate = jdbcTemplate;

        System.out.println( " < " + this.getClass().getName() + ".setJdbcTemplate = " + this.jdbcTemplate );
    }


    /**
     * This method is not used.
     * 
     * @return (unused)
     */
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate()
    {
        System.out.println( " < " + this.getClass().getName() + ".getNamedParameterJdbcTemplate = "
                + this.namedParameterJdbcTemplate );
        return namedParameterJdbcTemplate;
    }


    /**
     * This method is not used.
     * 
     * @param namedParameterJdbcTemplate
     *            (unused)
     */
    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate)
    {
        System.out.println( " > " + this.getClass().getName() + ".setNamedParameterJdbcTemplate" );
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        System.out.println( " < " + this.getClass().getName() + ".setNamedParameterJdbcTemplate = "
                + this.namedParameterJdbcTemplate );
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //  Using jdbcTemplate eliminates the need for code like this
    //
    //    public Circle getCircle(int circleId)
    //    {
    //        Connection conn = null;
    //
    //        try
    //        {
    //            conn = dataSource.getConnection();
    //
    //            PreparedStatement ps = conn.prepareStatement( "SELECT * FROM circle where id = ?" );
    //            ps.setInt( 1, circleId );
    //
    //            Circle circle = null;
    //            ResultSet rs = ps.executeQuery();
    //
    //            if ( rs.next() )
    //            {
    //                circle = new Circle( circleId, rs.getString( "name" ) );
    //            }
    //            rs.close();
    //            ps.close();
    //
    //            return circle;
    //        }
    //        catch ( Exception e )
    //        {
    //            throw new RuntimeException( e );
    //        }
    //        finally
    //        {
    //            try
    //            {
    //                conn.close();
    //            }
    //            catch ( SQLException e )
    //            {
    //                e.printStackTrace();
    //            }
    //        }
    //    }
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
