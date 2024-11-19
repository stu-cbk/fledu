package com.example.video.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class CreateVideo {
    private String baseUrl;
    private String baseImage;
    private String baseMp3;
    private String baseMp4;
    private Integer totalTask;

    /*
    public static void main(String[] args)
    {
        CreateVideo createVideo = new CreateVideo("1808537927105150978", 5);
        try {
            createVideo.create();
            createVideo.concat();
        }catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
    }*/

    public CreateVideo(String fileId, Integer totalTask)
    {
        this.baseUrl = "D:/flschool/data";
        //this.baseUrl = "/home/lzy/data";
        this.baseImage = this.baseUrl + "/images/" + fileId;
        this.baseMp3 = this.baseUrl + "/mp3/" + fileId;
        this.baseMp4 = this.baseUrl + "/mp4/" + fileId;
        this.totalTask = totalTask;
        // 创建输出目录（如果不存在）
        File outputDirFile = new File(this.baseMp4);
        if (!outputDirFile.exists()) {
            outputDirFile.mkdirs();
        }
        // 删除目录里的所有内容
        File[] files = outputDirFile.listFiles();
        if (files != null)
            for (File f : files)
                f.delete();
    }

    public void create() throws IOException,InterruptedException
    {
        for (int i = 0;i < this.totalTask;i ++)
        {
            List<String> command = new ArrayList<>();
            command.add("ffmpeg");
            command.add("-loop");
            command.add("1");
            command.add("-i");
            command.add(this.baseImage + "/slide-" + i + ".png");
            command.add("-i");
            command.add(this.baseMp3 + "/audio-" + i + ".mp3");
            command.add("-c:v");
            command.add("libx264");
            command.add("-tune");
            command.add("stillimage");
            command.add("-c:a");
            command.add("aac");
            command.add("-b:a");
            command.add("192k");
            command.add("-pix_fmt");
            command.add("yuv420p");
            command.add("-shortest");
            command.add("-strict");
            command.add("-2");
            command.add(this.baseMp4 + "/video-" + i + ".mp4");

            ProcessBuilder builder = new ProcessBuilder(command);
            builder.inheritIO().start().waitFor();
        }
        // 创建文件列表文件
        File fileList = new File(this.baseMp4 + "/fileList.txt");
        try (FileWriter writer = new FileWriter(fileList)) {
            for (int i = 0;i < this.totalTask;i ++) {
                writer.write("file '" + this.baseMp4 + "/video-" + i + ".mp4" + "'\n");
            }
        }
    }

    public void concat() throws IOException,InterruptedException
    {
        // 合并所有临时视频文件
        List<String> concatCommand = new ArrayList<>();
        concatCommand.add("ffmpeg");
        concatCommand.add("-f");
        concatCommand.add("concat");
        concatCommand.add("-safe");
        concatCommand.add("0");
        concatCommand.add("-i");
        concatCommand.add(this.baseMp4 + "/fileList.txt");
        concatCommand.add("-c");
        concatCommand.add("copy");
        concatCommand.add(this.baseMp4 + "/output.mp4");

        ProcessBuilder concatBuilder = new ProcessBuilder(concatCommand);
        concatBuilder.inheritIO().start().waitFor();
    }

}
