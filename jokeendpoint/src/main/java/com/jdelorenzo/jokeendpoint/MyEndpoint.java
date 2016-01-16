/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.jdelorenzo.jokeendpoint;

import com.example.JokeTeller;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "jokeendpoint.jdelorenzo.com",
    ownerName = "jokeendpoint.jdelorenzo.com",
    packagePath=""
  )
)
public class MyEndpoint {
    @ApiMethod(name = "getJoke")
    public Joke getJoke() {
        Joke response = new Joke();
        JokeTeller jokeTeller = new JokeTeller();
        response.setData(jokeTeller.getJoke());
        return response;
    }
}
