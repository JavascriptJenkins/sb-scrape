package com.genre.base.scraper.impl;

import org.springframework.stereotype.Component;
import com.genre.base.objects.HelloObject;
import com.genre.base.scraper.Hello;

import java.util.ArrayList;


@Component
public class HelloImpl implements Hello {

    public ArrayList<HelloObject> getHelloObjects(){

        // create a container to return the objects
        ArrayList<HelloObject> returnObjects = new ArrayList();

        // populate the returnObjects with some HelloObjects
        returnObjects = populateHelloObjects(returnObjects);

        // return the populated list of objects
        return returnObjects;

    }




    /*

    This method would normally call a webservice or connect to a database
    and query for HelloObjects to return.

    Right now it is mocking HelloObjects to simulate a
    database or webservice call.

     */
    private ArrayList<HelloObject> populateHelloObjects(ArrayList<HelloObject>  returnObjects){

        HelloObject helloObject1 = new HelloObject();
        helloObject1.setFirstName("james");
        helloObject1.setLastName("jameson");
        helloObject1.setAge(35);

        HelloObject helloObject2 = new HelloObject();
        helloObject2.setFirstName("preethi");
        helloObject2.setLastName("deepthi");
        helloObject2.setAge(40);

        HelloObject helloObject3 = new HelloObject();
        helloObject3.setFirstName("taylor");
        helloObject3.setLastName("taylorson");
        helloObject3.setAge(26);

        returnObjects.add(helloObject1);
        returnObjects.add(helloObject2);
        returnObjects.add(helloObject3);

        return returnObjects;

    }




}
