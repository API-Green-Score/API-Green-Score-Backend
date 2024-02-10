package fr.apithinking.apigreenscore.coreweb.smoketest;

import fr.apithinking.apigreenscore.core.smoketest.SmokeTestEndpoint;
import fr.apithinking.apigreenscore.core.smoketest.model.SmokeTest;
import fr.apithinking.apigreenscore.core.smoketest.model.SmokeTestCase;
import fr.apithinking.apigreenscore.corelang.thread.InitializableThreadLocal;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.annotation.EndpointWebExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.text.MessageFormat;

@EndpointWebExtension(endpoint = SmokeTestEndpoint.class)
@Slf4j
public class SmokeTestEndpointWebExtension {

    private static final String LS = System.getProperty("line.separator");

    @Value("classpath:/smoketest/smoketest.template.html")
    private Resource template;

    private String templateContent;

    private final SmokeTestEndpoint delegate;

    private static final InitializableThreadLocal<StringBuilder> TL_SB = new InitializableThreadLocal<>(StringBuilder.class, 200);

    public SmokeTestEndpointWebExtension(final SmokeTestEndpoint delegate) {

        this.delegate = delegate;
    }

    @PostConstruct
    public void init() {
        try (InputStream is = template.getInputStream()) {
            try (InputStreamReader isr = new InputStreamReader(is)) {
                templateContent = escape(FileCopyUtils.copyToString(isr));
            }
        } catch (IOException e) {
            LOGGER.error("Error reading smoketests html template", e);
            throw new UncheckedIOException(e);
        }
    }

    @ReadOperation(produces = MediaType.TEXT_HTML_VALUE)
    public WebEndpointResponse<String> smokeTestHtml(final SecurityContext securityContext) {

        StringBuilder sb = TL_SB.getCleanValue();
        for (SmokeTest groupe : this.delegate.smokeTest().getResults()) {
            renderSmokeTest(sb, groupe);
        }

        String pageHTML = MessageFormat.format(templateContent, sb.toString());

        return new WebEndpointResponse<>(pageHTML, WebEndpointResponse.STATUS_OK);
    }

    /**
     * Escape any single quote characters that are included in the specified
     * message string.
     *
     * @param pString The string to be escaped
     */
    private String escape(final String pString) {

        if (pString == null) {
            return pString;
        }

        int n = pString.length();
        StringBuilder sb = TL_SB.getCleanValue();

        for (int i = 0; i < n; i++) {
            char ch = pString.charAt(i);

            if (ch == '\'') {
                sb.append('\'').append(ch);
            } else if (ch == '{') {

                if (pString.charAt(i + 1) < '0' || pString.charAt(i + 1) > '9') {
                    sb.append("'").append(ch).append("'");
                } else {
                    sb.append(ch);
                }
            } else {
                sb.append(ch);
            }
        }

        return sb.toString();

    }

    /**
     * Rendu pour un groupe de resultats de smoke tests.
     *
     * @param pStringBuilder String builder pour ecrire le resultat.
     * @param pResultats     Groupe de resultats de tests.
     */
    private void renderSmokeTest(final StringBuilder pStringBuilder, final SmokeTest pResultats) {
        pStringBuilder.append("<div class=\"panel panel-primary\">").append(LS);
        pStringBuilder.append("<div class=\"panel-heading\"><h3 class=\"panel-title\">").append(pResultats.getName()).append("</h3></div>").append(LS);
        pStringBuilder.append("<ul class=\"list-group\">").append(LS);

        for (SmokeTestCase resultat : pResultats.getCases()) {
            renderTest(pStringBuilder, resultat);
        }

        pStringBuilder.append("</ul>").append(LS);
        pStringBuilder.append("</div>").append(LS);
    }

    /**
     * Rendu pour le resultat d'un smoke test.
     *
     * @param pStringBuilder String builder pour ecrire le resultat.
     * @param pResultat      Resultat a ecrire.
     */
    private void renderTest(final StringBuilder pStringBuilder, final SmokeTestCase pResultat) {

        String icon = "";
        String listStyle = "";
        switch (pResultat.getStatus()) {
            case SUCCESS:
                listStyle = "list-group-item-success";
                icon = "glyphicon-ok";
                break;
            case IGNORE:
                listStyle = "disabled";
                icon = "glyphicon-ban-circle";
                break;
            case WARNING:
                listStyle = "list-group-item-warning";
                icon = "glyphicon-warning-sign";
                break;
            case ERROR:
                listStyle = "list-group-item-danger";
                icon = "glyphicon-remove";
                break;
            default:
                break;
        }

        pStringBuilder.append("<li class=\"list-group-item ").append(listStyle).append("\">").append(LS);
        pStringBuilder.append("<span class=\"glyphicon ").append(icon).append(" pull-right\"></span>").append(LS);
        pStringBuilder.append("<span class=\"text-muted\">ID : </span><span>").append(pResultat.getId()).append("</span><br>").append(LS);
        pStringBuilder.append("<span class=\"text-muted\">").append(pResultat.getName()).append("</span>").append(LS);

        String message = pResultat.getMessage();
        if (StringUtils.isNotBlank(message)) {
            pStringBuilder.append(": ").append(message).append(LS);
        }

        pStringBuilder.append("</li>").append(LS);
    }

}