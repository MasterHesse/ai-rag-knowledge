package com.masterhesse.mh.dev.tech.test;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.PathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class JGitTest {
    @Resource
    private OllamaChatClient ollamaChatClient;

    @Resource
    private TokenTextSplitter tokenTextSplitter;

    @Resource
    private SimpleVectorStore simpleVectorStore;

    @Resource
    private PgVectorStore pgVectorStore;

    @Test
    public void test() throws Exception {
        String repoURL = "https://github.com/MasterHesse/InformationSystem_EduCourseCatalog";
        String username = "MasterHesse";
        String password = "";

        String localPath = "./cloned-repo";
        log.info("克隆路径："+new File(localPath).getAbsolutePath());

        FileUtils.deleteDirectory(new File(localPath));

        Git git = Git.cloneRepository()
                .setURI(repoURL)
                .setDirectory(new File(localPath))
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password))
                .call();

        git.close();

    }

    @Test
    public void test_file() throws IOException {
        Files.walkFileTree(Paths.get("./cloned-repo"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                try {
                    log.info("文件路径：" + file.toString());

                    PathResource resource = new PathResource(file);

                    TikaDocumentReader reader = new TikaDocumentReader(resource);

                    List<Document> documents = reader.get();
                    List<Document> documentSplitterList = tokenTextSplitter.apply(documents);

                    documents.forEach(doc -> doc.getMetadata().put("knowledge", "InformationSystem_EduCourseCatalog"));
                    documentSplitterList.forEach(doc -> doc.getMetadata().put("knowledge", "InformationSystem_EduCourseCatalog"));

                    pgVectorStore.accept(documentSplitterList);
                } catch (RuntimeException e) {
                    log.warn("处理文件时跳过: " + file + ", 原因: " + e.getMessage());
                    return FileVisitResult.CONTINUE;
                }

                return FileVisitResult.CONTINUE;
            }
        });
    }
}
