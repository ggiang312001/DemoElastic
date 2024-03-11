package com.dtnd.elastic.ElasticSearch.controller;

import com.dtnd.elastic.ElasticSearch.dto.response.DashboardResponseDTO;
import com.dtnd.elastic.ElasticSearch.entity.Log;
import com.dtnd.elastic.ElasticSearch.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/apis")
@CrossOrigin(origins = "http://192.168.100.59:3001", maxAge = 3600)
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/viewlog")
    public Iterable<Log> viewLog(@RequestParam(name = "search") String search){
        return logService.getLogs(search);
    }

    @GetMapping("/viewlog/{id}")
    public Log getLogById(@PathVariable String id){
        return logService.getLogById(id);
    }

    @GetMapping("/dashboard")
    public List<DashboardResponseDTO> viewLogPer5Minutes(@RequestParam(name = "date") String date) throws ParseException {
        return logService.viewLogPer5Minutes(date);
    }

    @PostMapping("/insertlog")
    public Log insertLog(@RequestBody Log log){
        return logService.insertLog(log);
    }

    @DeleteMapping("/deletelog/{id}")
    public void deleteLog(@PathVariable String id){
       logService.deleteLog(id);
    }

    @DeleteMapping("/deleteall")
    public void deleteAllg(){
       logService.deleteall();
    }


}
