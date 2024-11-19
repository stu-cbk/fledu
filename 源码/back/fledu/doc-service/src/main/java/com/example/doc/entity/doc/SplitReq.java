package com.example.doc.entity.doc;

import lombok.Data;

import java.util.List;

@Data
public class SplitReq {
    private List<String> fids;
    private boolean isSplitDefault;
    private String splitType;
    private Datas wikiSplitExtends;

    @Data
    public static class Datas{
        private List<String> chunkSeparators;
        private int chunkSize;
        private int minChunkSize;
    }
}
