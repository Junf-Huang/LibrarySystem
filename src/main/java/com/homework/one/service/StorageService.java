package com.homework.one.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init(String folder);

    void store(MultipartFile file, String folder);

    Stream<Path> loadAll();

    Path load(String filename, String folder);

    Resource loadAsResource(String filename, String folder);

    void deleteAll(String folder);

}
