package com.homework.one.service;


import com.homework.one.storage.StorageException;
import com.homework.one.storage.StorageFileNotFoundException;
import com.homework.one.storage.StorageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final static Logger logger= LoggerFactory.getLogger(FileSystemStorageService.class);
    private Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void store(MultipartFile file, String folder) {
        String  filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            logger.info("rootLocation={}", this.rootLocation);
            Path path = Paths.get(this.rootLocation + "/" + folder);
            Files.copy(file.getInputStream(), path.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String folder, String fileName) {
        Path uidpath = Paths.get(rootLocation+ "/"+ folder);
        return uidpath.resolve(fileName);
    }


    @Override
    public Resource loadAsResource(String folder, String fileName) {
        try {
            Path file = load(folder, fileName);
            logger.info("path={}", file.toString());
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + fileName);
            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + fileName, e);
        }
    }


    @Override
    public void deleteAll(String folder) {
        Path path = Paths.get(rootLocation + "/" + folder);
        FileSystemUtils.deleteRecursively(path.toFile());
    }

    public void init(String str){

        Path path = Paths.get(rootLocation + "/"+ str);
        try {
            File file = new File(path.toString());
            if (!file.exists()) {
                Files.createDirectory(path);
                logger.info("Directory is created!");
            } else {
                logger.info("Failed to create directory!");
            }
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

}
