package domainmodelhexa.learningtest.archunit.application;

import domainmodelhexa.learningtest.archunit.adapter.MyAdapter;

public class MyService {
    MyService2 myService2;

    void run(){
        myService2 = new MyService2();
        System.out.println(myService2);
    }
}
