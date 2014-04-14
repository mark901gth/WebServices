package com.preludesoftware.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.preludesoftware.dao.DaoImplementation;
import com.preludesoftware.model.CheckRecord;
import com.preludesoftware.model.GetOrigChkIdDao;
import com.preludesoftware.model.XmlOperations;

/**
 * Servlet for /GetOrigChkId
 * <p>
 * Servlet implementation class getOrigChkID
 * </p>
 * @author Mark Levine
 * @version 0.2 - &copy; Prelude Software, 2014.
 */
//@RequestMapping("/GetOrigChkID")
public class GetOrigChkID extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    DaoImplementation         dao              = null;
    GetOrigChkIdDao           daoGetData       = null;


    public GetOrigChkID()
    {
        System.out.println( "--calling spring.xml" );
        ApplicationContext ctx = new ClassPathXmlApplicationContext( "spring.xml" );

        System.out.println( "--calling getBean for MyDaoImpl" );
        dao = ctx.getBean( "MyDaoImpl", DaoImplementation.class );

        System.out.println( "--creating daoGetData" );
        daoGetData = new GetOrigChkIdDao( dao.getJdbcTemplate() );
    }


    /**
     * The web server calls this method when the user or web browser initiates a get operation.
     * <p>
     * The get operation is initiated by a URL of the form http://website/webpage.html?OrigID=12345
     * The results are returned as XML.
     * </p>
     * @param request request from web server
     * @param response response to web server
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println( this.getClass().getName() + ".doGet" );

        // Set response type to XML
        response.setContentType( "text/xml" );

        // Extract the parameter value
        String OrigID = request.getParameter( "OrigID" );

        // Call the servlet
        String xml = XmlOperations.createXmlFromResultSet( daoGetData.get_Chk_ID_by_OrigID_asSqlRowSet( OrigID ) );

        // Send response
        PrintWriter resp = response.getWriter();
        resp.print( xml );
    }


    /**
     * The web server calls this method when the user or web browser initiates a post operation via a form.
     * <p>
     * The post operation is initiated by a URL of the form http://website/webpage.html
     * The results are returned as HTML.
     * </p>
     * @param request request from web server
     * @param response response to web server
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException
    {
        System.out.println( this.getClass().getName() + ".doPost" );
        
        // Set response type to html
        response.setContentType( "text/html" );
        PrintWriter resp = response.getWriter();

        // Extract parameter from from form
        String OrigID = request.getParameter( "OrigID" );
        if ( OrigID.trim().equals( "" ) )
        {
            resp.print( "OrigID field is required" );
            return;
        }

        resp.print( "doPost OrigID = " + OrigID + ", Matching IDs:<br>" );
        try
        {
            // Call servlet to get list of matching CheckRecords
            List<CheckRecord> ckList = daoGetData.get_Chk_ID_by_OrigID_asList( OrigID.trim() );
            
            // Display results
            if ( ckList.size() == 0 )
            {
                resp.print( "no matching records were found" );
            }
            else
            {
                for ( int i = 0 ; i < ckList.size() ; i++ )
                {
                    resp.print( "   chkID = " + ckList.get( i ).getID() + "<br>" );
                }                
            }
        }
        catch ( Exception e )
        {
            resp.print( "record not found" );
        }
    }

}
