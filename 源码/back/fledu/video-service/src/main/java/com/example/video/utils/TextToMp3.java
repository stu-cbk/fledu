package com.example.video.utils;

import com.alibaba.fastjson.JSONArray;
import com.example.video.clients.Mp3Client;
import com.example.video.entity.request.QueryReq;
import com.example.video.entity.response.QueryResp;
import lombok.Data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Data
public class TextToMp3 {
    private final String fileId;
    private final JSONArray jsonArray;
    private JSONArray status;
    private final Mp3Client mp3Client;
    private final String outputDir;

    public TextToMp3(String fileId, JSONArray jsonArray,JSONArray status, Mp3Client mp3Client){
        this.fileId = fileId;
        this.jsonArray = jsonArray;
        this.status = status;
        this.mp3Client = mp3Client;
        this.outputDir = "D:/flschool/data/mp3/" + fileId;
        //this.outputDir = "/home/lzy/data/mp3/" + fileId;
        File outputDirFile = new File(outputDir);
        if (!outputDirFile.exists()) {
            outputDirFile.mkdirs();
        }
    }

    public int queryAudio(String appId) throws IOException
    {
        int siz = this.status.size(), cnt = 0;
        for (int i = 0;i < siz;i ++)
        {
            if (status.get(i).toString().equals("1"))
            {
                cnt ++;
                continue;
            }
            String taskId = jsonArray.get(i).toString();
            QueryReq queryReq = new QueryReq(appId,taskId);
            QueryResp queryResp = this.mp3Client.query(queryReq);
            if (queryResp.getHeader().getCode() == 0)
            {
                String audioBase64 = queryResp.getPayload().getAudio().getAudio();
                byte[] decode = Base64.getDecoder().decode(audioBase64);
                String audioUrl = new String(decode);
                byte[] bytes = HttpUtil.getBytes(audioUrl);

                // 生成文件路径和名称
                String outputFilePath = this.outputDir + "/audio-" + i + ".mp3";
                File outputFile = new File(outputFilePath);

                // 将字节数组写入文件
                try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                    fos.write(bytes);
                }
                this.status.set(i, 1);
                cnt ++;
            }else break;
        }
        return cnt * 100 / siz;
    }

}
