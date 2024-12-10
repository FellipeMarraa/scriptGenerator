package com.esocial.script.generator.controller;

import com.esocial.script.generator.model.DbScript;
import com.esocial.script.generator.model.ViewData;
import com.esocial.script.generator.service.ScriptGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/script")
public class ScriptGeneratorController {

    @Autowired
    private ScriptGeneratorService scriptGeneratorService;

    @PostMapping("/generate")
    public String generateScript(@RequestBody ViewData viewData, @RequestBody DbScript dbScript) {
        return scriptGeneratorService.generateScript(dbScript, viewData);
    }
}