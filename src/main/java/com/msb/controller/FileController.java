package com.msb.controller;

import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2023-12-05 17:22
 */
@RestController
public class FileController {

    @RequestMapping("/single")
    public Mono<String> singleFile(@RequestPart("file")Mono<FilePart> file) {

        return file.map(filePart -> {
            Path tempFile = null;
            try {
                tempFile = Files.createTempFile("file-", filePart.name());
                System.out.println(tempFile.toAbsolutePath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // 异步文件channel
            AsynchronousFileChannel channel = null;
            try {
                channel = AsynchronousFileChannel.open(tempFile, StandardOpenOption.WRITE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            DataBufferUtils.write(filePart.content(), channel, 0)
                    .doOnNext(System.out::println)
                    .doOnComplete(()-> {
                        System.out.println("文件拷贝完成");
                    }).subscribe();

            // 封装了文件信息
            return tempFile;
        }).map(tmp -> tmp.toFile())
                .flatMap(file1 -> file.map(FilePart::filename));

    }

    @RequestMapping("/multi")
    public Mono<List<String>> multiFiles(@RequestPart("file") Flux<FilePart> filePartFlux) {
        return
                filePartFlux.map(filePart -> {
                    Path tempFile = null;
                    try {
                        tempFile = Files.createTempFile("mfile-", filePart.name());
                        System.out.println(tempFile.toAbsolutePath());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("文件路径："+tempFile.toAbsolutePath());

                    // 底层是零拷贝
                    filePart.transferTo(tempFile.toFile());

                    return tempFile;
                }).map(tmp -> tmp.toFile())
                        .flatMap(file1 -> filePartFlux.map(FilePart::filename))
                        .collectList();
    }

}
