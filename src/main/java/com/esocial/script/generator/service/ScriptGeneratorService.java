package com.esocial.script.generator.service;

import com.esocial.script.generator.model.DbScript;
import com.esocial.script.generator.model.ViewData;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ScriptGeneratorService {

    public void saveToFile(DbScript dbScript, ViewData viewData) throws IOException {
        String script = generateScript(dbScript, viewData);

        String userHome = System.getProperty("user.home");
        Path downloadPath = Paths.get(userHome, "Downloads", viewData.getNomeView() + ".xsql");

        Files.writeString(downloadPath, script);
    }

    public String generateScript(DbScript dbScript, ViewData viewData) {
        StringBuilder script = new StringBuilder();

        script.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        script.append("<dbscript numos=\"").append(dbScript.getNumos()).append("\">\n");

        script.append("<objeto>").append(dbScript.getObjeto()).append("</objeto>\n");
        script.append("<tipo>").append(dbScript.getTipo()).append("</tipo>\n");
        if (dbScript.getOrdem() != null) {
            script.append("<ordem>\n");
            dbScript.getOrdem().getApos().forEach(apos -> script.append("<apos>").append(apos).append("</apos>\n"));
            script.append("</ordem>\n");
        }
        script.append("<tabela>").append(dbScript.getTabela()).append("</tabela>\n");
        script.append("<objetivo>").append(dbScript.getObjetivo()).append("</objetivo>\n");
        script.append("<executar>").append(dbScript.getExecutar()).append("</executar>\n");
        script.append("<autor>").append(dbScript.getAutor()).append("</autor>\n");

        if (viewData != null) {
            String orclScript = generateOracleScript(viewData);
            String mssqlScript = generateMssqlScript(viewData);

            script.append("<orcl xml:space=\"preserve\"><![CDATA[").append(orclScript).append("]]></orcl>\n");
            script.append("<mssql xml:space=\"preserve\"><![CDATA[").append(mssqlScript).append("]]></mssql>\n");
        }

        script.append("</dbscript>\n");

        return script.toString();
    }

    private String generateOracleScript(ViewData viewData) {
        StringBuilder script = new StringBuilder();

        script.append("CREATE OR REPLACE VIEW ").append(viewData.getNomeView()).append(" AS\n");
        script.append("SELECT CHAVE\n");
        script.append("     , '{\"").append(viewData.getNomeTabela())
                .append("\":{\"PK\":\"CHAVE='''||").append(viewData.getNomeJson()).append(".CHAVELOCAL||'''\", \"COLUNAS\":['||SUBSTR(").append(viewData.getNomeJson()).append(".JSON, 3, LENGTH(").append(viewData.getNomeJson()).append(".JSON))||']}}' AS JSON\n");
        script.append("FROM (\n");

        script.append("    SELECT\n");
        viewData.getCampos().forEach((campo, tipoCampo) -> {
            String valorPadrao;
            String valorConversao;

            if (tipoCampo.equalsIgnoreCase("number")){
                valorConversao = "0";
                valorPadrao = "0";
            } else if (tipoCampo.equalsIgnoreCase("date")){
                valorConversao = "' '";
                valorPadrao = "SYSDATE";
            } else{
                valorConversao = "' '";
                valorPadrao = "' '";
            }

            script.append("        CASE WHEN NVL(N.").append(campo).append(", ").append(valorPadrao)
                    .append(") <> NVL(O.").append(campo).append(", ").append(valorPadrao).append(") ")
                    .append("THEN ', {\"COLUNA\":\"").append(campo)
                    .append("\",\"VALOROLD\":\"'||NVL(O.").append(campo).append(", ").append(valorConversao)
                    .append(")||'\",\"VALORNEW\":\"'||NVL(N.").append(campo).append(", ").append(valorConversao)
                    .append(")||'\"}' ELSE '' END ||\n");
        });
        script.delete(script.length() - 3, script.length());

        script.append(" AS JSON\n");
        script.append("        , P.CHAVE\n        , N.CHAVEPAI\n        , N.CHAVE AS CHAVELOCAL\n");
        script.append("    FROM TFPVAES VAES\n");
        script.append("        INNER JOIN ").append(viewData.getTabelaPai()).append(" P ON P.CODEMP = VAES.CODEMP AND P.TPAMB = VAES.TPAMB ")
                .append("AND P.DTREF = VAES.DTREF AND P.SEQUENCIA = VAES.SEQUENCIA AND P.DTREF_ANT IS NOT NULL\n");
        script.append("        INNER JOIN ").append(viewData.getNomeTabela()).append(" N ON N.CODEMP = VAES.CODEMP AND N.TPAMB = VAES.TPAMB ")
                .append("AND N.CHAVEPAI = P.CHAVE AND N.DTREF = VAES.DTREF AND N.SEQUENCIA = VAES.SEQUENCIA\n");
        script.append("        INNER JOIN ").append(viewData.getNomeTabela()).append(" O ON O.CODEMP = VAES.CODEMP AND O.TPAMB = VAES.TPAMB ")
                .append("AND O.CHAVEPAI = P.CHAVE AND O.DTREF = P.DTREF_ANT AND O.SEQUENCIA = P.SEQUENCIA_ANT\n");
        script.append("    WHERE VAES.NOMEEVENTO = '").append(viewData.getNomeEvento()).append("'\n");
        script.append("UNION\n");

        script.append("    SELECT\n");
        viewData.getCampos().forEach((campo, tipoCampo) -> {
            String valorConversao;

            if (tipoCampo.equalsIgnoreCase("number")){
                valorConversao = "0";
            } else{
                valorConversao = "' '";
            }

            script.append("        ', {\"COLUNA\":\"").append(campo)
                    .append("\",\"VALOROLD\":\"NAO EXISTIA\",\"VALORNEW\":\"'||NVL(N.")
                    .append(campo).append(", ").append(valorConversao).append(")||'\"}' ||\n");
        });
        script.delete(script.length() - 3, script.length());

        script.append(" AS JSON\n");
        script.append("        , P.CHAVE\n        , N.CHAVEPAI\n        , N.CHAVE AS CHAVELOCAL\n");
        script.append("    FROM TFPVAES VAES\n");
        script.append("        INNER JOIN ").append(viewData.getTabelaPai()).append(" P ON P.CODEMP = VAES.CODEMP AND P.TPAMB = VAES.TPAMB ")
                .append("AND P.DTREF = VAES.DTREF AND P.SEQUENCIA = VAES.SEQUENCIA AND P.DTREF_ANT IS NOT NULL\n");
        script.append("        INNER JOIN ").append(viewData.getNomeTabela()).append(" N ON N.CODEMP = VAES.CODEMP AND N.TPAMB = VAES.TPAMB ")
                .append("AND N.CHAVEPAI = P.CHAVE AND N.DTREF = VAES.DTREF AND N.SEQUENCIA = VAES.SEQUENCIA\n");
        script.append("    WHERE VAES.NOMEEVENTO = '").append(viewData.getNomeEvento()).append("'\n");
        script.append("        AND NOT EXISTS (\n");
        script.append("            SELECT 1 FROM ").append(viewData.getNomeTabela()).append(" O WHERE O.CODEMP = VAES.CODEMP AND O.TPAMB = VAES.TPAMB ")
                .append("AND O.CHAVEPAI = P.CHAVE AND O.DTREF = P.DTREF_ANT AND O.SEQUENCIA = P.SEQUENCIA_ANT\n");
        script.append("        )\n");
        script.append("UNION\n");

        script.append("    SELECT\n");
        viewData.getCampos().forEach((campo, tipoCampo) -> {
            String valorConversao;

            if (tipoCampo.equalsIgnoreCase("number")){
                valorConversao = "0";
            } else{
                valorConversao = "' '";
            }

            script.append("        ', {\"COLUNA\":\"").append(campo)
                    .append("\",\"VALOROLD\":\"'||NVL(O.").append(campo)
                    .append(", ").append(valorConversao).append(")||'\",\"VALORNEW\":\"DELETADO\"}' ||\n");
        });
        script.delete(script.length() - 3, script.length());

        script.append(" AS JSON\n");
        script.append("        , P.CHAVE\n        , O.CHAVEPAI\n        , O.CHAVE AS CHAVELOCAL\n");
        script.append("    FROM TFPVAES VAES\n");
        script.append("        INNER JOIN ").append(viewData.getTabelaPai()).append(" P ON P.CODEMP = VAES.CODEMP AND P.TPAMB = VAES.TPAMB ")
                .append("AND P.DTREF = VAES.DTREF AND P.SEQUENCIA = VAES.SEQUENCIA AND P.DTREF_ANT IS NOT NULL\n");
        script.append("        INNER JOIN ").append(viewData.getNomeTabela()).append(" O ON O.CODEMP = VAES.CODEMP AND O.TPAMB = VAES.TPAMB ")
                .append("AND O.CHAVEPAI = P.CHAVE AND O.DTREF = P.DTREF_ANT AND O.SEQUENCIA = P.SEQUENCIA_ANT\n");
        script.append("    WHERE VAES.NOMEEVENTO = '").append(viewData.getNomeEvento()).append("'\n");
        script.append("        AND NOT EXISTS (\n");
        script.append("            SELECT 1 FROM ").append(viewData.getNomeTabela()).append(" N WHERE N.CODEMP = VAES.CODEMP AND N.TPAMB = VAES.TPAMB ")
                .append("AND N.CHAVEPAI = P.CHAVE AND N.DTREF = VAES.DTREF AND N.SEQUENCIA = VAES.SEQUENCIA\n");
        script.append("        )\n");
        script.append(")").append(viewData.getNomeJson()).append("\n");
        script.append("WHERE LENGTH(NVL(").append(viewData.getNomeJson()).append(".JSON, '')) > 0\n /");

        return script.toString();
    }

    private String generateMssqlScript(ViewData viewData) {
        StringBuilder script = new StringBuilder();

        script.append("CREATE VIEW ").append(viewData.getNomeView()).append(" AS\n");
        script.append("SELECT CHAVE\n");
        script.append("   , '{\"").append(viewData.getNomeTabela())
                .append("\":{\"PK\":\"CHAVE=''' + ").append(viewData.getNomeJson()).append(".CHAVELOCAL + '''\", \"COLUNAS\":[' + SUBSTRING(CAST(").append(viewData.getNomeJson()).append(".JSON AS VARCHAR(MAX)), 3, LEN(CAST(").append(viewData.getNomeJson()).append(".JSON AS VARCHAR(MAX)))) + ']}}' AS JSON\n");
        script.append("FROM (\n");

        script.append("    SELECT\n");
        viewData.getCampos().forEach((campo, tipoCampo) -> {
            String valorPadrao;

            if (tipoCampo.equalsIgnoreCase("number")){
                valorPadrao = "0";
            } else{
                valorPadrao = "' '";
            }

            script.append("        CASE WHEN ISNULL(N.").append(campo).append(", ").append(valorPadrao)
                    .append(") <> ISNULL(O.").append(campo).append(", ").append(valorPadrao).append(") ")
                    .append("THEN ', {\"COLUNA\":\"").append(campo)
                    .append("\",\"VALOROLD\":\"' + ISNULL(CAST(O.").append(campo).append(" AS VARCHAR(1000)), '') + ")
                    .append("'\",\"VALORNEW\":\"' + ISNULL(CAST(N.").append(campo).append(" AS VARCHAR(1000)), '') + '\"}' ELSE '' END +\n");
        });
        script.delete(script.length() - 3, script.length());

        script.append(" AS JSON\n");
        script.append("        , P.CHAVE\n        , N.CHAVEPAI\n        , N.CHAVE AS CHAVELOCAL\n");
        script.append("    FROM TFPVAES VAES\n");
        script.append("        INNER JOIN ").append(viewData.getTabelaPai()).append(" P ON P.CODEMP = VAES.CODEMP AND P.TPAMB = VAES.TPAMB ")
                .append("AND P.DTREF = VAES.DTREF AND P.SEQUENCIA = VAES.SEQUENCIA AND P.DTREF_ANT IS NOT NULL\n");
        script.append("        INNER JOIN ").append(viewData.getNomeTabela()).append(" N ON N.CODEMP = VAES.CODEMP AND N.TPAMB = VAES.TPAMB ")
                .append("AND N.CHAVEPAI = P.CHAVE AND N.DTREF = VAES.DTREF AND N.SEQUENCIA = VAES.SEQUENCIA\n");
        script.append("        INNER JOIN ").append(viewData.getNomeTabela()).append(" O ON O.CODEMP = VAES.CODEMP AND O.TPAMB = VAES.TPAMB ")
                .append("AND O.CHAVEPAI = P.CHAVE AND O.DTREF = P.DTREF_ANT AND O.SEQUENCIA = P.SEQUENCIA_ANT\n");
        script.append("    WHERE VAES.NOMEEVENTO = '").append(viewData.getNomeEvento()).append("'\n");
        script.append("UNION ALL\n");

        script.append("    SELECT\n");
        viewData.getCampos().forEach((campo, tipoCampo) -> script.append("        ', {\"COLUNA\":\"").append(campo)
                .append("\",\"VALOROLD\":\"NAO EXISTIA\",\"VALORNEW\":\"' + ISNULL(CAST(N.")
                .append(campo).append(" AS VARCHAR(1000)), ' ') + '\"}' +\n"));
        script.delete(script.length() - 3, script.length());

        script.append(" AS JSON\n");
        script.append("        , P.CHAVE\n         , N.CHAVEPAI\n        , N.CHAVE AS CHAVELOCAL\n");
        script.append("    FROM TFPVAES VAES\n");
        script.append("        INNER JOIN ").append(viewData.getTabelaPai()).append(" P ON P.CODEMP = VAES.CODEMP AND P.TPAMB = VAES.TPAMB ")
                .append("AND P.DTREF = VAES.DTREF AND P.SEQUENCIA = VAES.SEQUENCIA AND P.DTREF_ANT IS NOT NULL\n");
        script.append("        INNER JOIN ").append(viewData.getNomeTabela()).append(" N ON N.CODEMP = VAES.CODEMP AND N.TPAMB = VAES.TPAMB ")
                .append("AND N.CHAVEPAI = P.CHAVE AND N.DTREF = VAES.DTREF AND N.SEQUENCIA = VAES.SEQUENCIA\n");
        script.append("    WHERE VAES.NOMEEVENTO = '").append(viewData.getNomeEvento()).append("'\n");
        script.append("        AND NOT EXISTS (\n");
        script.append("            SELECT 1 FROM ").append(viewData.getNomeTabela()).append(" O WHERE O.CODEMP = VAES.CODEMP AND O.TPAMB = VAES.TPAMB ")
                .append("AND O.CHAVEPAI = P.CHAVE AND O.DTREF = P.DTREF_ANT AND O.SEQUENCIA = P.SEQUENCIA_ANT\n");
        script.append("        )\n");
        script.append("UNION ALL\n");

        script.append("    SELECT\n");
        viewData.getCampos().forEach((campo, tipoCampo) -> {
            script.append("        ', {\"COLUNA\":\"").append(campo)
                    .append("\",\"VALOROLD\":\"' + ISNULL(CAST(O.").append(campo).append(" AS VARCHAR(1000)), ' ') + ")
                    .append("'\",\"VALORNEW\":\"DELETADO\"}' +\n");
        });
        script.delete(script.length() - 3, script.length());

        script.append(" AS JSON\n");
        script.append("        , P.CHAVE\n        , O.CHAVEPAI\n        , O.CHAVE AS CHAVELOCAL\n");
        script.append("    FROM TFPVAES VAES\n");
        script.append("        INNER JOIN ").append(viewData.getTabelaPai()).append(" P ON P.CODEMP = VAES.CODEMP AND P.TPAMB = VAES.TPAMB ")
                .append("AND P.DTREF = VAES.DTREF AND P.SEQUENCIA = VAES.SEQUENCIA AND P.DTREF_ANT IS NOT NULL\n");
        script.append("        INNER JOIN ").append(viewData.getNomeTabela()).append(" O ON O.CODEMP = VAES.CODEMP AND O.TPAMB = VAES.TPAMB ")
                .append("AND O.CHAVEPAI = P.CHAVE AND O.DTREF = P.DTREF_ANT AND O.SEQUENCIA = P.SEQUENCIA_ANT\n");
        script.append("    WHERE VAES.NOMEEVENTO = '").append(viewData.getNomeEvento()).append("'\n");
        script.append("        AND NOT EXISTS (\n");
        script.append("            SELECT 1 FROM ").append(viewData.getNomeTabela()).append(" N WHERE N.CODEMP = VAES.CODEMP AND N.TPAMB = VAES.TPAMB ")
                .append("AND N.CHAVEPAI = P.CHAVE AND N.DTREF = VAES.DTREF AND N.SEQUENCIA = VAES.SEQUENCIA\n");
        script.append("        )\n");
        script.append(")").append(viewData.getNomeJson()).append("\n");
        script.append("WHERE LEN(ISNULL(").append(viewData.getNomeJson()).append(".JSON, '')) > 0\n GO");

        return script.toString();
    }
}