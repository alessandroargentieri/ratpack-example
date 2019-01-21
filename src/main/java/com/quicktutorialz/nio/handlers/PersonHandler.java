package com.quicktutorialz.nio.handlers;

import com.quicktutorialz.nio.entities.Person;
import ratpack.exec.Promise;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import static ratpack.jackson.Jackson.fromJson;
import static ratpack.jackson.Jackson.json;

/**
 * async handler
 */
public class PersonHandler implements Handler {

    @Override
    public void handle(Context context) throws Exception {
        Promise<Person> personPromise = context.parse(fromJson(Person.class));
        personPromise.then(person -> context.render(json(transform(person))));
    }

    private Person transform(Person p){
        if(p!=null){
            p.setTitle(p.getTitle().toUpperCase());
            p.setName(p.getName().toUpperCase());
            p.setSurname(p.getSurname().toUpperCase());
        }
        return p;
    }
}
