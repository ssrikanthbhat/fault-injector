package com.expedia.fault.injector.springboot;

import com.expedia.fault.injector.FaultInjector;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
@RequestMapping(path = "/__fault")
public class FaultInjectorController {
    private final FaultInjector faultInjector;

    public FaultInjectorController(FaultInjector faultInjector) {
        this.faultInjector = faultInjector;
    }

    @RequestMapping(method = {GET}, produces = {"application/json"})
    public ResponseEntity root() {
        final Map entity = new HashMap<String, Object>();
        entity.put("ok", true);
        entity.put("message", "It's not your fault");
        return ResponseEntity.ok().body(entity);
    }

    @RequestMapping(path = {"/cpu"}, method = {POST}, produces = {"application/json"})
    public ResponseEntity cpu() {
        faultInjector.cpu();
        final Map entity = new HashMap<String, Object>();
        entity.put("ok", true);
        return ResponseEntity.ok().body(entity);
    }

    @RequestMapping(path = {"/memory"}, method = {POST}, produces = {"application/json"})
    public ResponseEntity memory() {
        faultInjector.memory();
        final Map entity = new HashMap<String, Object>();
        entity.put("ok", true);
        return ResponseEntity.ok().body(entity);
    }

    @RequestMapping(path = {"/disk"}, method = {POST}, produces = {"application/json"})
    public ResponseEntity disk() {
        faultInjector.disk();
        final Map entity = new HashMap<String, Object>();
        entity.put("ok", true);
        return ResponseEntity.ok().body(entity);
    }

}
