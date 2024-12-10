package com.esocial.script.generator;

import com.esocial.script.generator.model.DbScript;
import com.esocial.script.generator.model.Ordem;
import com.esocial.script.generator.model.ViewData;
import com.esocial.script.generator.service.ScriptGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ScriptGeneratorApplication implements CommandLineRunner {

	@Autowired
	private ScriptGeneratorService scriptGeneratorService;

	public static void main(String[] args) {
		SpringApplication.run(ScriptGeneratorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Criando o objeto DbScript
		DbScript dbScript = new DbScript();
		dbScript.setNumos("1190695");
		dbScript.setObjeto("VGF_ALTER_S1200_REMOUTREMPRE");
		dbScript.setTipo("VIEW");

		Ordem ordem1 = new Ordem();

		ordem1.setApos(Arrays.asList("DROP_VGF_ALTER_S1200_REMOUTREMPRE", "APOS TESTE"));
		dbScript.setOrdem(ordem1);
		dbScript.setTabela("");
		dbScript.setObjetivo("CRIA - VGF_ALTER_S1200_REMOUTREMPRE");
		dbScript.setExecutar("SE_NAO_EXISTIR");
		dbScript.setAutor("CARLOSM");

		ViewData viewData = new ViewData();
		viewData.setTabelaPai("TFPS1200");
		viewData.setNomeTabela("TFPS1200_REMOUTREMPRE");
		viewData.setNomeView("VGF_ALTER_S1200_REMOUTREMPRE");
		viewData.setCampos(Arrays.asList("TPINSC", "NRINSC", "CODCATEG", "VLRREMUNOE"));
		viewData.setNomeEvento("S-1200");
		viewData.setNomeJson("S1200");

		Map<String, String> tiposCampos = new HashMap<>();
		tiposCampos.put("TPINSC", "Number");
		tiposCampos.put("NRINSC", "String");
		tiposCampos.put("CODCATEG", "Number");
		tiposCampos.put("VLRREMUNOE", "Number");

		viewData.setTiposCampos(tiposCampos);

		// Gerando o script (com CDATA já tratado)
		scriptGeneratorService.saveToFile(dbScript, viewData);
		System.out.println("Arquivo XML gerado com sucesso!");
	}
}