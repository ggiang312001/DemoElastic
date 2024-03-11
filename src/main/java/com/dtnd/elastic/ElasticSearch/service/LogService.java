package com.dtnd.elastic.ElasticSearch.service;

import com.dtnd.elastic.ElasticSearch.dto.response.DashboardResponseDTO;
import com.dtnd.elastic.ElasticSearch.entity.Log;
import com.dtnd.elastic.ElasticSearch.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;
    public Iterable<Log>  getLogs(String searchRequest){
        if(!searchRequest.isEmpty()){
            Criteria criteria = new Criteria("client.user")
                    .contains(searchRequest)
                    .or(new Criteria("client.id").contains(searchRequest))
                    .or(new Criteria("client.ip").contains(searchRequest))
                    .or(new Criteria("client.ip").contains(searchRequest))
                    .or(new Criteria("client.browser").contains(searchRequest))
                    .or(new Criteria("client.project").contains(searchRequest))
                    .or(new Criteria("client.group").contains(searchRequest))
                    .or(new Criteria("client.cmd").contains(searchRequest))
                    .or(new Criteria("client.path").contains(searchRequest))
                    .or(new Criteria("client.method").contains(searchRequest))
                    .or(new Criteria("client.status").contains(searchRequest));

            Query query = new CriteriaQuery(criteria);
            SearchHits<Log> searchHits = elasticsearchOperations.search(query, Log.class);

            return searchHits.getSearchHits()
                    .stream()
                    .map(SearchHit::getContent)
                    .toList();
        }
        return logRepository.findAll();
    }

    public List<DashboardResponseDTO> viewLogPer5Minutes (String date) throws ParseException {

        List<DashboardResponseDTO> result = new ArrayList<>();
        LocalTime startTime = LocalTime.of(0, 0,0);
        LocalTime endTime = LocalTime.of(23, 59, 59);
        int intervalMinutes = 5;
        int temp = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        LocalTime nextTime = startTime.plusMinutes(intervalMinutes);

        LocalDate currentDate = LocalDate.parse(date); // Đổi kiểu ngày từ String sang LocalDate

        for (LocalTime currentTime = startTime; currentTime.isBefore(endTime); currentTime = currentTime.plusMinutes(intervalMinutes)) {
            if(currentTime.equals(startTime)){
                temp++;
                if (temp==2) {
                    break;  // Kết thúc vòng lặp khi currentTime là thời gian kết thúc cuối cùng của ngày
                }
            }
            // Chuyển đổi LocalTime sang Date
            Date current = Date.from(currentDate.atTime(currentTime).atZone(ZoneId.systemDefault()).toInstant());
            Date next = Date.from(currentDate.atTime(nextTime).atZone(ZoneId.systemDefault()).toInstant());
            Criteria timeCriteria = new Criteria("api.time").between(current, next);
            Query query = new CriteriaQuery(timeCriteria);
            SearchHits<Log> searchHits = elasticsearchOperations.search(query, Log.class);

            DashboardResponseDTO dashboardResponseDTO = new DashboardResponseDTO();
            dashboardResponseDTO.setStartTime( dateFormat.parse(dateFormat.format(current)));
            dashboardResponseDTO.setEndTime( dateFormat.parse(dateFormat.format(next)));
            dashboardResponseDTO.setCountLog(searchHits.getTotalHits());
            dashboardResponseDTO.setDetails(searchHits.getSearchHits()
                    .stream()
                    .map(SearchHit::getContent)
                    .toList());
            result.add(dashboardResponseDTO);
            nextTime = nextTime.plusMinutes(intervalMinutes);
        }
        return result;
    }

    public Log getLogById(String id){
        return logRepository.findById(id).get();
    }

    public Log insertLog(Log log){
        return logRepository.save(log);
    }

    public void deleteLog(String id){
        logRepository.deleteById(id);
    }

    public void deleteall(){
        logRepository.deleteAll();
    }




}
