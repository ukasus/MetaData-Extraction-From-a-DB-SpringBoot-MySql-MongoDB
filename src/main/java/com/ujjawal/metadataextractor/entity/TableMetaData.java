package com.ujjawal.metadataextractor.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;


@Document(collection = "TableMetaData")

public class TableMetaData {



    private String tableName;
    private List<ColumnData> columnData;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnData> getColumnData() {
        return columnData;
    }

    public void setColumnData(List<ColumnData> columnData) {
        this.columnData = columnData;
    }
}
