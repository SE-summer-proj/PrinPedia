package com.prinpedia.backend;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = {"test"})
@RunWith(JUnitPlatform.class)
@SelectPackages({
    "com.prinpedia.backend.daoImpl",
    "com.prinpedia.backend.serviceImpl",
    "com.prinpedia.backend.controller",
    "com.prinpedia.backend.security"
})
class BackendApplicationTests {
//    @Test
//    void contextLoads() {
//
//    }
}
