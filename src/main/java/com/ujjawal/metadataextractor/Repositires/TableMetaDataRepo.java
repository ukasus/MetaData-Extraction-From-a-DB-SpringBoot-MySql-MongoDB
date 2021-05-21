package com.ujjawal.metadataextractor.Repositires;

import com.ujjawal.metadataextractor.entity.TableMetaData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TableMetaDataRepo  extends MongoRepository<TableMetaData,String> {
}
