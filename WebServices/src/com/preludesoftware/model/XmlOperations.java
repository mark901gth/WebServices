package com.preludesoftware.model;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Convert Spring Jdbc SqlrowSet to XML.
 * <p>
 * Contains the methods that are used convert a Spring JDBC SqlRowSet to XML.
 * </p>
 * 
 * @author Mark Levine
 * @version 0.2 - &copy; Prelude Software, 2014.
 */

public class XmlOperations
{
    private static Integer indent = 2;


    /**
     * Creates a string containing the XML representation of the SqlRowSet
     * <p>
     * The first section of the XML is metadata containing the number of data rows
     * and a list of the column names, types, and sizes.
     * </p>
     * <p>
     * The second section containes the data rows.
     * </p>
     * 
     * @param sqlRowSet
     *              org.springframework.jdbc.support.rowset.SqlRowSet
     * @return
     *              String Containing the XML
     */
    public static String createXmlFromResultSet(SqlRowSet sqlRowSet)
    {
        // Return string to contain the generated XML
        String xmlString = null;
        
        // Extract meta data for the SqlRowSet
        SqlRowSetMetaData sqlRowSetMetaData = sqlRowSet.getMetaData();

        try
        {
            // Create new XML document
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element resultSet = doc.createElement( "resultSet" );
            doc.appendChild( resultSet );

            // Process meta data
            
            if ( sqlRowSetMetaData != null )
            {
                // Column names, types, and sizes
                
                // <metaData>
                Element metaData = doc.createElement( "metaData" );
                resultSet.appendChild( metaData );

                // <columns>
                Element columns = doc.createElement( "columns" );
                metaData.appendChild( columns );

                Attr numberOfColumns = doc.createAttribute( "numberOfColumns" );
                numberOfColumns.setValue( ( (Integer) sqlRowSetMetaData.getColumnCount() ).toString() );
                columns.setAttributeNode( numberOfColumns );

                // For each column returned
                for ( int i = 1 ; i <= sqlRowSetMetaData.getColumnCount() ; i++ )
                {
                    // <column name="name" type="type" size="size"/>
                    Element column = doc.createElement( "column" );
                    columns.appendChild( column );

                    Attr name = doc.createAttribute( "name" );
                    name.setNodeValue( sqlRowSetMetaData.getColumnName( i ) );
                    column.setAttributeNode( name );

                    Attr type = doc.createAttribute( "type" );
                    type.setNodeValue( sqlRowSetMetaData.getColumnTypeName( i ) );
                    column.setAttributeNode( type );

                    Attr size = doc.createAttribute( "size" );
                    size.setNodeValue( ( (Integer) sqlRowSetMetaData.getColumnDisplaySize( i ) ).toString() );
                    column.setAttributeNode( size );
                }
            }

            // Process data rows

            // <dataRows numberOfRows="123">
            Element dataRows = doc.createElement( "dataRows" );
            resultSet.appendChild( dataRows );

            // Initialize
            Integer numRows = 0;
            sqlRowSet.beforeFirst();
            
            // For each data row
            while ( sqlRowSet.next() )
            {
                numRows += 1;

                // <row rowNumer="1">
                Element row = doc.createElement( "row" );
                dataRows.appendChild( row );
                Attr rowNumber = doc.createAttribute( "rowNumber" );
                rowNumber.setNodeValue( numRows.toString() );
                row.setAttributeNode( rowNumber );

                // For each column in the data row
                for ( int i = 1 ; i <= sqlRowSetMetaData.getColumnCount() ; i++ )
                {
                    // <column name="name" value="value"/>
                    Element column = doc.createElement( "column" );
                    row.appendChild( column );

                    Attr name = doc.createAttribute( "name" );
                    name.setNodeValue( sqlRowSetMetaData.getColumnName( i ) );
                    column.setAttributeNode( name );

                    Attr value = doc.createAttribute( "value" );
                    value.setNodeValue( sqlRowSet.getString( i ) );
                    column.setAttributeNode( value );
                }
            }
            
            // Set the number of rows (numRows) to the <dataRows numberOfRows="numRows"> tag
            Attr numberOfRows = doc.createAttribute( "numberOfRows" );
            numberOfRows.setNodeValue( numRows.toString() );
            dataRows.setAttributeNode( numberOfRows );

            // Create the XML output to a string
            
            StringWriter stringWriter = new StringWriter();
            StreamResult result = new StreamResult( stringWriter );
            DOMSource source = new DOMSource( doc );

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            //transformerFactory.setAttribute("indent-number", "4"); // this works
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty( OutputKeys.INDENT, "yes" );
            transformer.setOutputProperty( OutputKeys.METHOD, "xml" );
            transformer.setOutputProperty( "{http://xml.apache.org/xslt}indent-amount", String.valueOf( getIndent() ) );
            transformer.transform( source, result );

            // Set the return value
            xmlString = stringWriter.toString();
        }
        catch ( ParserConfigurationException | TransformerException e )
        {
            e.printStackTrace();
        }
        
        return xmlString;
    }


    
    /**
     * 
     * @return
     *              Number of spaces used for indenting XML tags.
     */
    public static Integer getIndent()
    {
        return indent;
    }


    /**
     * 
     * @param indent
     *              Number of spaces used for indenting XML tags.
     */
    public static void setIndent(Integer indent)
    {
        XmlOperations.indent = indent;
    }
}
