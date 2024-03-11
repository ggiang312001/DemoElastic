package com.dtnd.elastic.ElasticSearch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Client {
    private String user;
    private String id;
    private String ip;
    private String browser;
}
