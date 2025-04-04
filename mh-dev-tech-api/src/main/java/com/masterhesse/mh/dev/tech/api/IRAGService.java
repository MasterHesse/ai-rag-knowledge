package com.masterhesse.mh.dev.tech.api;


import com.masterhesse.mh.dev.tech.api.response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRAGService {

    Response<List<String>> queryRagList();

    Response<String> uploadFile(String ragTag, List<MultipartFile> files);

}
