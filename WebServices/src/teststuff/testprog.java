package teststuff;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.preludesoftware.dao.DaoImplementation;
import com.preludesoftware.model.CheckRecord;
import com.preludesoftware.model.GetOrigChkIdDao;
import com.preludesoftware.model.XmlOperations;

public class testprog
{

    public static void main(String[] args)
    {
        System.out.println( "##calling spring.xml" );
        ApplicationContext ctx = new ClassPathXmlApplicationContext( "spring.xml" );

        System.out.println( "##calling getBean for MyDaoImpl" );
        DaoImplementation dao = ctx.getBean( "MyDaoImpl", DaoImplementation.class );

        System.out.println( "##creating daoGetData" );
        GetOrigChkIdDao daoGetData = new GetOrigChkIdDao( dao.getJdbcTemplate() );

        System.out.println();
        System.out.println( "##Executing queries" );

        List<CheckRecord> checkRecordList;
        String outputFormat = "   %-8s  %-10s\n";
        String sId = "0";

        System.out.println();
        sId = "12345";
        checkRecordList = daoGetData.get_Chk_ID_by_OrigID_asList( sId );
        System.out.println( "--records returned: " + checkRecordList.size() );
        System.out.printf( outputFormat, "Orig ID", "ID" );
        for ( CheckRecord checkRecord : checkRecordList )
        {
            System.out.printf( outputFormat, checkRecord.getOrigID(), checkRecord.getID() );
        }

        System.out.println();
        sId = "12345";
        SqlRowSet sqlRowSet = daoGetData.get_Chk_ID_by_OrigID_asSqlRowSet( sId );
        XmlOperations.setIndent( 2 );
        System.out.println( XmlOperations.createXmlFromResultSet( sqlRowSet ) );

        System.out.println();
        sId = "8224";
        checkRecordList = daoGetData.get_Chk_ID_by_OrigID_asList( sId );
        System.out.println( "--records returned: " + checkRecordList.size() );
        System.out.printf( outputFormat, "Orig ID", "ID" );
        for ( CheckRecord checkRecord : checkRecordList )
        {
            System.out.printf( outputFormat, checkRecord.getOrigID(), checkRecord.getID() );
        }

        System.out.println();
        System.out.println( "##Done." );
    }

}