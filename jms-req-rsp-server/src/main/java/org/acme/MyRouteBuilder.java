package org.acme;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.smallrye.common.annotation.Identifier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Typed;
import jakarta.jms.ConnectionFactory;
import jakarta.ws.rs.Produces;

public class MyRouteBuilder extends RouteBuilder {

    @Produces
    @Typed({ ConnectionFactory.class })
    @ApplicationScoped
    @Identifier("externally-defined")
    @Default
    ActiveMQConnectionFactory externallyDefinedConnectionFactory(
            @ConfigProperty(name = "my.jms.server.externally-defined.url") String externallyDefinedUrl,
            @ConfigProperty(name = "my.jms.server.externally-defined.username") String username,
            @ConfigProperty(name = "my.jms.server.externally-defined.password") String password
            
            
            ) {
        return new ActiveMQConnectionFactory(externallyDefinedUrl, username, password);
    }

    @Override
    public void configure() throws Exception {
        from("sjms2:queue:FILA.ENTRADA")
                // TODO fazer alguma conversao da mensagem
                .log("Recebendo mensagem JMS: ${body}")
                .to("https://echo-api.3scale.net/")
                // TODO fazer alguma conversao da resposta
                .log("Recebendo resposta HTTP: ${body}")
                .to("sjms2:queue:FILA.SAIDA")
                .log("Mensagem respondida com sucesso ${body}");
    }

}
