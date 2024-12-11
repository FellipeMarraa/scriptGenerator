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
        dbScript.setObjeto(newScriptDTO.getNomeView());
        dbScript.setTipo("VIEW");
        dbScript.setOrdem(newScriptDTO.getOrdem());
        dbScript.setTabela("");
        dbScript.setObjetivo("CRIAR - " + viewData.getNomeView());
        dbScript.setExecutar("SE_NAO_EXISTIR");
        dbScript.setAutor(newScriptDTO.getAutor());
        dbScript.setOrcl(null);
        dbScript.setMssql(null);

        viewData.setTabelaPai(newScriptDTO.getTabelaPai());
        viewData.setNomeTabela(newScriptDTO.getNomeTabela());
        viewData.setNomeView(newScriptDTO.getNomeView());
        viewData.setNomeEvento(newScriptDTO.getNomeEvento());
        viewData.setNomeJson(newScriptDTO.getNomeEvento().replace("-", ""));
        viewData.setCampos(newScriptDTO.getCampos());

        return scriptGeneratorService.generateScript(dbScript, viewData);
    }

    @GetMapping("/download")
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
        viewData.setNomeEvento(newScriptDTO.getNomeEvento());
        viewData.setNomeJson(newScriptDTO.getNomeJson());
        viewData.setCampos(newScriptDTO.getCampos());

        try {
            scriptGeneratorService.saveToFile(dbScript, viewData);
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

    }

    @GetMapping("/test-deploy")
    public String testDeploy(@RequestParam(defaultValue = "User") String name) {
        return "Deploy test successful! Hello, " + name + "!";
    }
}