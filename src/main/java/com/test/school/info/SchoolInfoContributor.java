package com.test.school.info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SchoolInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        Map<String, String> map = new HashMap<>();

        map.put("Application Name", "School Web Application");
        map.put("Application Description", "School Web Application for Students and Admin");
        map.put("App Version", "1.0.0.");
        map.put("Contact Email", "info@school.com");
        map.put("Contact Mobile Number", "+90 123 456 123");

        builder.withDetail("school-info", map);
    }
}
