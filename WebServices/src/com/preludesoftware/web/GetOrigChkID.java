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
import org.springframework.web.bind.annotation.RequestMapping;

import com.preludesoftware.dao.DaoImplementation;
import com.preludesoftware.model.CheckRecord;
import com.preludesoftware.model.GetOrigChkIdDao;

/**
 * Servlet implementation class getOrigChkID
 */
//@RequestMapping("/GetOrigChkID")
public class GetOrigChkID extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    DaoImplementation         dao              = null; // = new DaoImpl();
    GetOrigChkIdDao                daoGetData       = null;


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
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println( this.getClass().getName() + ".doGet" );

        response.setContentType( "text/html" );
        PrintWriter resp = response.getWriter();

        String OrigID = request.getParameter( "OrigID" );
        List<CheckRecord> checkRecordList = daoGetData.getChk_ID_by_OrigID( OrigID );

        resp.println( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" );
        resp.println( "<" + this.getClass().getSimpleName() + ">" );
        resp.println( "  <num_records>" + daoGetData.getChk_ID_by_OrigID( OrigID ).size() + "</num_records>" );
        for ( CheckRecord checkRecord : checkRecordList )
        {
            resp.println( "  <data_record>" );
            resp.println( "    <OrigID>" + checkRecord.getOrigID() + "</OrigID>" );
            resp.println( "    <ID>" + checkRecord.getID() + "</ID>" );
            resp.println( "  </data_record>" );
        }
        resp.println( "</" + this.getClass().getSimpleName() + ">" );
    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException
    {
        System.out.println( this.getClass().getName() + ".doPost" );
        response.setContentType( "text/html" );
        PrintWriter resp = response.getWriter();

        String OrigID = request.getParameter( "OrigID" );

        resp.print( "doPost OrigID = " + OrigID + "<br>" );
        try
        {
            resp.print( "chkID = " + daoGetData.getChk_ID_by_OrigID( OrigID ).get( 0 ).getID() );
        }
        catch ( Exception e )
        {
            resp.print( "record not found" );
        }
        System.out.println( "doPost OrigID = " + OrigID );
    }

}
