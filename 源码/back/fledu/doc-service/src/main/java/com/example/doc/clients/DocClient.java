package com.example.doc.clients;

import com.example.doc.entity.doc.SplitReq;
import com.example.doc.entity.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "doc-service", url = "https://chatdoc.xfyun.cn/openapi")
public interface DocClient {
    @PostMapping(value = "/v1/file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    UploadResp upload(@RequestPart("file") MultipartFile file, @RequestPart("fileType") String fileType);
    @PostMapping(value = "/v1/file/info", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    InfoResp info(@RequestPart("fileId") String fileId);
    @PostMapping(value = "/v1/file/split", consumes = MediaType.APPLICATION_JSON_VALUE)
    Resp split(@RequestBody SplitReq splitReq);
    @PostMapping(value = "/v1/file/embedding", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Resp vector(@RequestPart("fileIds") String fileIds);
    @PostMapping(value = "/v1/file/summary/start", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    SummaryStartResp summaryStart(@RequestPart("fileId") String fileId);
    @PostMapping(value = "/v1/file/summary/query", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    SummaryQueryResp summaryQuery(@RequestPart("fileId") String fileId);
}
