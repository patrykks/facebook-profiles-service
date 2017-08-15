package pl.patrykks.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.patrykks.datasources.FacebookDataSource;
import pl.patrykks.datasources.impl.InMemoryFacebookDataSource;
import pl.patrykks.domain.Facebook;
import pl.patrykks.mapping.FacebookJsonToObjectMapper;
import pl.patrykks.exceptions.NotFoundException;
import pl.patrykks.services.FacebookService;
import pl.patrykks.services.impl.DefaultFacebookService;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.Scanner;

public class ServiceUsageExample {
    private static final Logger logger = LoggerFactory.getLogger(ServiceUsageExample.class);
    private static final String[] PROFILE_FILES = {
            "sample-data/f1.json",
            "sample-data/f2.json",
            "sample-data/f3.json",
            "sample-data/f4.json",
            "sample-data/f5.json"};

    public static void main(String[] args) throws NotFoundException {
        FacebookDataSource facebookDataSource = new InMemoryFacebookDataSource();
        FacebookJsonToObjectMapper facebookMapper = new FacebookJsonToObjectMapper();

        for (String fileWithProfile : PROFILE_FILES) {
            String stringifiedFacebookProfile = readFile(fileWithProfile);
            Optional<Facebook> facebookProfile = facebookMapper.map(stringifiedFacebookProfile);
            facebookProfile.ifPresent(facebookDataSource::insert);
        }

        FacebookService facebookService = new DefaultFacebookService(facebookDataSource);
        facebookService.findAll().stream().map(Facebook::toString).forEach(logger::info);
    }

    private static String readFile(String filePath) {
        InputStream inputStream = ServiceUsageExample.class.getClassLoader().getResourceAsStream(filePath);

        if (inputStream == null) {
            logger.error("JSON file containing input data cannot be found:" + filePath);
            throw new RuntimeException("JSON file containing input data cannot be found");
        }

        Scanner scanner = new Scanner(new BufferedInputStream(inputStream));
        String fileContent = scanner.useDelimiter("\\Z").next();
        scanner.close();

        return fileContent;
    }
}
