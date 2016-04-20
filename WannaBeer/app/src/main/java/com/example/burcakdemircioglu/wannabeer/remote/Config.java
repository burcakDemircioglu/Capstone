package com.example.burcakdemircioglu.wannabeer.remote;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by burcakdemircioglu on 19/04/16.
 */
public class Config {
    public static final URL BASE_URL;

    static {
        URL url = null;
        try {
            url = new URL("https://db.tt/b6vOGaPR" );
        } catch (MalformedURLException ignored) {
            // TODO: throw a real error
        }

        BASE_URL = url;
    }
}
