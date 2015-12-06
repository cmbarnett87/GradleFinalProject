/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.cmbar.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;
import com.example.JavaJokesCorey;

/** An endpoint class we are exposing */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.myapplication.cmbar.example.com",
    ownerName = "backend.myapplication.cmbar.example.com",
    packagePath=""
  )
)
public class MyEndpoint {

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);

        return response;
    }

    @ApiMethod(name = "tellJoke")
    public MyBean tellJoke(@Named("joke") String joke) {
        MyBean response = new MyBean();
        response.setData(joke);
        return response;
    }

    @ApiMethod(name = "getJoke")
    public MyBean getJoke() {
        MyBean response = new MyBean();
        JavaJokesCorey joke = new JavaJokesCorey();
        response.setData(joke.tellJavaJokeGCE());
        return response;
    }

}
