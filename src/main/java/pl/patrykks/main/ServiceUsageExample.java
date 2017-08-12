package pl.patrykks.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.patrykks.dao.FacebookDAO;
import pl.patrykks.dao.impl.FlatFileFacebookDAO;
import pl.patrykks.domain.Facebook;
import pl.patrykks.mapping.FacebookJsonToObjectMapper;
import pl.patrykks.exceptions.NotFoundException;
import pl.patrykks.service.FacebookService;
import pl.patrykks.service.impl.DefaultFacebookService;

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
        FacebookDAO facebookDAO = new FlatFileFacebookDAO();
        FacebookJsonToObjectMapper facebookMapper = new FacebookJsonToObjectMapper();

        for (String profileFile : PROFILE_FILES) {
            String stringifiedFacebookProfile = readFile(profileFile);
            Optional<Facebook> facebookProfile = facebookMapper.map(stringifiedFacebookProfile);
            facebookProfile.ifPresent(facebookDAO::insert);
        }

        FacebookService facebookService = new DefaultFacebookService(facebookDAO);
        facebookService.findAll().stream().map(Facebook::toString).forEach(logger::info);
    }

    private static String readFile(String filePath) {
        InputStream inputStream = ServiceUsageExample.class.getClassLoader().getResourceAsStream(filePath);

        if (inputStream == null) {
            logger.error("JSON file containing input data cannot be found:" + filePath);
            throw new RuntimeException("JSON file containing input data cannot be found");
        }

        return new Scanner(inputStream).useDelimiter("\\A").next();
    }
}
