package com.dtnd.elastic.ElasticSearch.entity;

import co.elastic.clients.util.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class API {
    private String project;
    private String group;
    private String cmd;
    private String path;
    private String method;
    private Object data;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date time;
    private Object result;
}
