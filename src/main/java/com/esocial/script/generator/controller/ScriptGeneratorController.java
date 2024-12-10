package com.esocial.script.generator.controller;

import com.esocial.script.generator.model.DbScript;
import com.esocial.script.generator.model.NewScriptDTO;
import com.esocial.script.generator.model.ViewData;
import com.esocial.script.generator.service.ScriptGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/script")
public class ScriptGeneratorController {

    @Autowired
    private ScriptGeneratorService scriptGeneratorService;

    @PostMapping("/generate")
    public String generateScript(@RequestBody NewScriptDTO newScriptDTO) {
        DbScript dbScript = new DbScript();
        ViewData viewData = new ViewData();

        dbScript.setNumos(newScriptDTO.getNumos());
        dbScript.setObjeto(newScriptDTO.getObjeto());
        dbScript.setTipo(newScriptDTO.getTipo());
        dbScript.setOrdem(newScriptDTO.getOrdem());
        dbScript.setTabela(newScriptDTO.getTabela());
        dbScript.setObjetivo(newScriptDTO.getObjetivo());
        dbScript.setExecutar(newScriptDTO.getExecutar());
        dbScript.setAutor(newScriptDTO.getAutor());
        dbScript.setOrcl(newScriptDTO.getOrcl());
        dbScript.setMssql(newScriptDTO.getMssql());

        viewData.setTabelaPai(newScriptDTO.getTabelaPai());
        viewData.setNomeTabela(newScriptDTO.getNomeTabela());
        viewData.setNomeView(newScriptDTO.getNomeView());
        viewData.setCampos(newScriptDTO.getCampos());
        viewData.setNomeEvento(newScriptDTO.getNomeEvento());
        viewData.setNomeJson(newScriptDTO.getNomeJson());
        viewData.setTiposCampos(newScriptDTO.getTiposCampos());

        return scriptGeneratorService.generateScript(dbScript, viewData);
    }

    @PostMapping("/download")
    public void downloadScript(@RequestBody NewScriptDTO newScriptDTO) throws IOException {
        DbScript dbScript = new DbScript();
        ViewData viewData = new ViewData();

        dbScript.setNumos(newScriptDTO.getNumos());
        dbScript.setObjeto(newScriptDTO.getObjeto());
        dbScript.setTipo(newScriptDTO.getTipo());
        dbScript.setOrdem(newScriptDTO.getOrdem());
        dbScript.setTabela(newScriptDTO.getTabela());
        dbScript.setObjetivo(newScriptDTO.getObjetivo());
        dbScript.setExecutar(newScriptDTO.getExecutar());
        dbScript.setAutor(newScriptDTO.getAutor());
        dbScript.setOrcl(newScriptDTO.getOrcl());
        dbScript.setMssql(newScriptDTO.getMssql());

        viewData.setTabelaPai(newScriptDTO.getTabelaPai());
        viewData.setNomeTabela(newScriptDTO.getNomeTabela());
        viewData.setNomeView(newScriptDTO.getNomeView());
        viewData.setCampos(newScriptDTO.getCampos());
        viewData.setNomeEvento(newScriptDTO.getNomeEvento());
        viewData.setNomeJson(newScriptDTO.getNomeJson());
        viewData.setTiposCampos(newScriptDTO.getTiposCampos());

        scriptGeneratorService.saveToFile(dbScript, viewData);
    }

    @GetMapping("/test-deploy")
    public String testDeploy(@RequestParam(defaultValue = "User") String name) {
        return "Deploy test successful! Hello, " + name + "!";
    }
}