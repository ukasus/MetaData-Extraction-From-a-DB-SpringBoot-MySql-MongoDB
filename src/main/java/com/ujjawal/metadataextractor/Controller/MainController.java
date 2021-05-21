package com.ujjawal.metadataextractor.Controller;

import com.ujjawal.metadataextractor.DatabaseToBeExtracted.JDBCConnector;
import com.ujjawal.metadataextractor.DatabaseToBeExtracted.Query;
import com.ujjawal.metadataextractor.Repositires.TableMetaDataRepo;
import com.ujjawal.metadataextractor.entity.ColumnData;
import com.ujjawal.metadataextractor.entity.TableMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MainController {
    @Autowired
    JDBCConnector jdbcConnector;

    @Autowired
    TableMetaDataRepo tableMetaDataRepo;



    @RequestMapping("/extractMetaData")
    public String fetchMetadata()
    {
        String result="";

        try{

            //Get Connection to db(MySql)

            Connection conn= jdbcConnector.getConnection();

            DatabaseMetaData meta=conn.getMetaData();


            //Fetch all metaData about tables in given schema
            ResultSet rsTables=meta.getTables(null,null,null,new String[]{"TABLE"});

            while (rsTables.next())
            {
               // System.out.println("TableName -> "+rsTables.getString("TABLE_NAME"));

                TableMetaData tableMetaData=new TableMetaData();
                tableMetaData.setTableName(rsTables.getString("TABLE_NAME"));

                //Fetch all metaData of columns for a table
                ResultSet rsColumns=meta.getColumns(null,null,rsTables.getString("TABLE_NAME"),null);
                List<ColumnData> columnDataList=new ArrayList<>();
                while(rsColumns.next())
                {

                    //System.out.println("    Column -> "+rsColumns.getString("COLUMN_NAME")+" ," +rsColumns.getString("DATA_TYPE"));

                    ColumnData columnData=new ColumnData();
                    columnData.setColumnName(rsColumns.getString("COLUMN_NAME"));
                    int dataType=Integer.parseInt(rsColumns.getString("DATA_TYPE"));

                    String columnDataType="";
                    if(dataType==4)
                        columnDataType="Integer";
                    else if(dataType==12)
                        columnDataType="Varchar";
                    else if(dataType==-5)
                        columnDataType="Big int";
                    else if(dataType==16)
                        columnDataType="Boolean";
                    else if(dataType==93)
                        columnDataType="TimeStamp";
                    else if(dataType==-6)
                        columnDataType="Tiny int";
                    else
                        columnDataType=rsColumns.getString("DATA_TYPE");

                    columnData.setColumnDataType(columnDataType);

                    columnDataList.add(columnData);

                }
                tableMetaData.setColumnData(columnDataList);

                //Save tableMetaData as a record in MongoDbAtlas
                tableMetaDataRepo.save(tableMetaData);


            }
            result="MetaData Successfully extracted from oldDB(MySql) and saved to newDb(MongoDB Atlas)";


        }
        catch (Exception e)
        {
            e.printStackTrace();
            result="MetaData Extraction failed,see the logs of the applicaiton";
        }





        return result;
    }


    //Execute query on Mysql(Old Db)
    @PostMapping("/oldDb/insertData")
    public String insertData(@RequestBody Query query) {

        if (jdbcConnector.executeQueryToOldDb(query.getQuery())==true)
            return "Query successfully executed";
        else
            return "Invalid query";


    }

}
