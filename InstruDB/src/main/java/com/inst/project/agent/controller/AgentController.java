package com.inst.project.agent.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

    @PostMapping("/storage")
    public ResponseEntity<String> receiveStorage( @RequestBody Map<String, Object> data ) {

        System.out.println("Agent 데이터 수신");
        System.out.println(data);

        return ResponseEntity.ok("SUCCESS");
    }
}
