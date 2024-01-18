package org.acme;

import org.apache.camel.builder.RouteBuilder;

public class MyRouteBuilder extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        from("sjms2:queue:FILA.ENTRADA")
            //TODO fazer alguma conversao da mensagem
            .log("Recebendo mensagem JMS: ${body}")
            .to("https://echo-api.3scale.net/")
            //TODO fazer alguma conversao da resposta
            .log("Recebendo resposta HTTP: ${body}")
        .to("sjms2:queue:FILA.SAIDA")
        .log("Mensagem respondida com sucesso ${body}")
        ;
    }
    
}
