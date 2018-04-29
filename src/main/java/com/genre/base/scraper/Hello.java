package com.genre.base.scraper;

import org.springframework.stereotype.Component;
import com.genre.base.objects.HelloObject;

import java.util.ArrayList;


@Component
public interface Hello {


    ArrayList<HelloObject> getHelloObjects();




}
