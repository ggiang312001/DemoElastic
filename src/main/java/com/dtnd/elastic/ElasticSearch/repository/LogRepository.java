package com.dtnd.elastic.ElasticSearch.repository;

import co.elastic.clients.util.DateTime;
import com.dtnd.elastic.ElasticSearch.entity.Log;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Date;
import java.util.List;


public interface LogRepository extends ElasticsearchRepository<Log, String> {

    @Query("{\"range\": {\"requestTime\": {\"gte\": \"?0\", \"lte\": \"?1\"}}}")
    List<Log> findLogsInTimeRange(Date currentTime, Date nextTime);
}
