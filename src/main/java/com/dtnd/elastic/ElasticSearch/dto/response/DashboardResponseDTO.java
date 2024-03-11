package com.dtnd.elastic.ElasticSearch.dto.response;

import co.elastic.clients.util.DateTime;
import com.dtnd.elastic.ElasticSearch.entity.Log;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DashboardResponseDTO {
    private Date startTime;
    private Date endTime;
    private long countLog;
    private List<Log> details;

}
