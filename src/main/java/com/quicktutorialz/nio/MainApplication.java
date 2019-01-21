package com.quicktutorialz.nio;

import com.quicktutorialz.nio.entities.Person;
import com.quicktutorialz.nio.handlers.PersonHandler;
import ratpack.server.RatpackServer;

import static ratpack.jackson.Jackson.fromJson;
import static ratpack.jackson.Jackson.json;

public class MainApplication {

    public static void main(String[] args) throws Exception {

        RatpackServer.start(server -> server.handlers(chain -> chain
            .get("", ctx -> ctx.render("Ratpack app running"))
            .get("hi", ctx -> ctx.render("Hi"))
            .get("hello/:name?", ctx -> ctx.render("Hello " + ctx.getPathTokens().toString())) //pathparam not-required
            .get("greet/:name", ctx -> ctx.render("Hello " + ctx.getPathTokens().toString()))  //pathparam required

            .get("ciao", ctx -> ctx.getResponse().contentType("plain/text").status(200).send("Ciao"))

            .get("buongiorno", ctx -> {
                String name = ctx.getRequest().getQueryParams().get("name");
                ctx.getResponse().beforeSend(response -> {
                    response.cookie("DNT", "1");
                    response.status(200);
                    response.contentType("text/plain");
                }).send(name);
            })

            //async methods
            .post("person", new PersonHandler())
            .post("async", ctx -> ctx.parse(fromJson(Person.class)).then(p -> ctx.render(json(p))))

           )


        );



    }






}
