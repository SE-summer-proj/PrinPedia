package com.prinpedia.backend;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectPackages({"com.prinpedia.backend.controller", "com.prinpedia.backend.serviceImpl"})
class BackendApplicationTests {
//    @Test
//    void contextLoads() {
//
//    }
}
