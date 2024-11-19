package com.example.video.utils;

import lombok.Value;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class PPTToImage {
    private String fileId;

    private String uploadDir;
    public PPTToImage(String fileId) {
        this.fileId = fileId;
        this.uploadDir = "D:/flschool/data";
        //this.uploadDir = "/home/lzy/data";
    }

    public void savePPT(MultipartFile multipartFile) throws IOException
    {
        String upload = this.uploadDir + "/pptx/" + this.fileId + ".pptx";
        File file = new File(upload);
        multipartFile.transferTo(file);
        System.out.println("成功存储ppt fileId:" + this.fileId);
    }

    public int transImage(MultipartFile multipartFile) throws IOException
    {
        String upload = this.uploadDir + "/images/" + this.fileId;
        try (InputStream inputStream = multipartFile.getInputStream();
             XMLSlideShow ppt = new XMLSlideShow(inputStream)) {
            // 创建输出目录（如果不存在）
            File outputDirFile = new File(upload);
            if (!outputDirFile.exists()) {
                outputDirFile.mkdirs();
            }
            // 删除目录里的所有内容
            File[] files = outputDirFile.listFiles();
            if (files != null)
                for (File f : files)
                    f.delete();
            return processAndSaveSlides(ppt, upload);
        }
    }

    public int processAndSaveSlides(XMLSlideShow ppt, String outputDir) throws IOException {
        Dimension pgsize = ppt.getPageSize();
        int width = pgsize.width;
        int height = pgsize.height;

        int slideNumber = 0;

        for (XSLFSlide slide : ppt.getSlides()) {
            // 解决中文乱码问题
            for (XSLFShape shape : slide.getShapes()) {
                if (shape instanceof XSLFTextShape) {
                    XSLFTextShape txtshape = (XSLFTextShape) shape;
                    for (XSLFTextParagraph textPara : txtshape.getTextParagraphs()) {
                        for (XSLFTextRun textRun : textPara.getTextRuns()) {
                            textRun.setFontFamily("Microsoft YaHei");
                        }
                    }
                }
            }

            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = img.createGraphics();
            graphics.setPaint(Color.white);
            graphics.fill(new Rectangle2D.Float(0, 0, width, height));

            // 渲染幻灯片
            slide.draw(graphics);

            // 生成文件路径和名称
            String outputFilePath = outputDir + "/slide-" + slideNumber + ".png";
            File outputFile = new File(outputFilePath);
            System.out.println(outputFilePath);
            // 将BufferedImage保存为图片文件
            ImageIO.write(img, "png", outputFile);

            slideNumber++;
        }
        System.out.println("成功存储图片 fileId:" + this.fileId);
        return slideNumber;
    }

}
