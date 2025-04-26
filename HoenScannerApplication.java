package com.skyscanner;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HoenScannerApplication extends Application<HoensbannerConfiguration> {
    public static void main(String[] args) throws Exception {
        new HoenScannerApplication().run(args);
    }

    @Override
    public void run(final HoensbannerConfiguration configuration, final Environment environment) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<SearchResult> carResults = Arrays.asList(
            mapper.readValue(
                getClass().getClassLoader().getResource("rental_cars.json"),
                SearchResult[].class
            )
        );
        List<SearchResult> hotelResults = Arrays.asList(
            mapper.readValue(
                getClass().getClassLoader().getResource("hotels.json"),
                SearchResult[].class
            )
        );
        List<SearchResult> searchResults = new ArrayList<>();
        searchResults.addAll(carResults);
        searchResults.addAll(hotelResults);
        final SearchResource resource = new SearchResource(searchResults);
        environment.jersey().register(resource);
    }
}
