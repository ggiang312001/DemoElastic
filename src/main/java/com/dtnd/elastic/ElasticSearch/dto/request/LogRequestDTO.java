package com.dtnd.elastic.ElasticSearch.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogRequestDTO {
    private String user;
    private String ip;
    private String id;
    private String browser;
    private String project;
    private String group;
    private String cmd;
    private String path;
    private String method;
    private String status;
}
