package com.udacity.gradle.builditbigger;

//interface defining callbacks for RetrieveJokeTask
public interface OnJokeLoaded {
    void onJokeLoaded(String jokeText);
}
