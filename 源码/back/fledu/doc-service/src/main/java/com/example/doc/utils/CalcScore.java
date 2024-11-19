package com.example.doc.utils;


import java.util.List;

public class CalcScore {

    final static int[][] anxietyScore={
            {1,2,3,4},{1,2,3,4},{1,2,3,4},{1,2,3,4},{4,3,2,1},
            {1,2,3,4},{1,2,3,4},{1,2,3,4},{4,3,2,1},{1,2,3,4},
            {1,2,3,4},{1,2,3,4},{4,3,2,1},{1,2,3,4},{1,2,3,4},
            {1,2,3,4},{4,3,2,1},{1,2,3,4},{4,3,2,1},{1,2,3,4}
    };
    final static int[][] aloneScore={
            {4,3,2,1},{1,2,3,4},{1,2,3,4},{1,2,3,4},{4,3,2,1},
            {4,3,2,1},{1,2,3,4},{1,2,3,4},{4,3,2,1},{4,3,2,1},
            {1,2,3,4},{1,2,3,4},{1,2,3,4},{1,2,3,4},{4,3,2,1},
            {4,3,2,1},{1,2,3,4},{1,2,3,4},{4,3,2,1},{4,3,2,1}
    };
    final static int[][] depressionScore={
            {1,2,3,4},{4,3,2,1},{1,2,3,4},{1,2,3,4},{4,3,2,1},
            {4,3,2,1},{1,2,3,4},{1,2,3,4},{1,2,3,4},{1,2,3,4},
            {4,3,2,1},{4,3,2,1},{1,2,3,4},{4,3,2,1},{1,2,3,4},
            {4,3,2,1},{1,2,3,4},{1,2,3,4},{4,3,2,1},{4,3,2,1}
    };
    final static int[][] matureScore={
            {-3,-2, 4, 0, 6}, { 4, 0,-3, 8,-4}, {-4,10, 0, 5,-3}, { 0, 3,-3, 8,-2}, {-3, 8, 4,-2, 0},
            {-3,-2, 8, 4, 0}, {-2, 6,-3, 0, 8}, {-5, 0, 6, 4,-3}, {-4, 8, 0,-2, 3}, { 8,-4,-4, 0, 4},
            {-2, 0, 8,-4, 6}, {-2, 4, 8,-4, 6}, { 0, 2,10,-4,-3}, {-1, 8, 0,-3, 4}, { 0, 6, 4,-2,-4},
            { 8, 0, 4,-2,-4}, {-1, 0,-4, 8, 4}, {-2, 0, 8,-3, 4}, {-2, 6, 0,-3, 4}, {-2, 0, 8,-3, 4},
            {-1, 4, 8, 2,-4}, {-5, 3,-2,10, 0}, { 0, 8,-1,-4, 4}, {-3,-2, 6, 0,10}, { 4, 8, 0,-4,-1}
    };
    final static int[][] socialScore={
            {0,1},{1,0},{0,1},{0,1},{1,0},{0,1},{0,1},{1,0},{0,1},{1,0},
            {1,0},{0,1},{1,0},{1,0},{0,1},{1,0},{0,1},{1,0},{0,1},{1,0},
            {1,0},{0,1},{0,1},{1,0},{1,0},{1,0},{0,1},{0,1}
    };

    static public int getAnxScore(List<Integer> answer){
        int score = 0;
        for (int i = 0;i < answer.size();i ++){
            int c = answer.get(i);
            if (c < 0 || c > 3) return 0;
            score += anxietyScore[i][c];
        }
        return score;
    }

    static public int getAloneScore(List<Integer> answer){
        int score = 0;
        for (int i = 0;i < answer.size();i ++){
            int c = answer.get(i);
            if (c < 0 || c > 3) return 0;
            score += aloneScore[i][c];
        }
        return score;
    }

    static public int getDelayScore(List<Integer> answer){
        int score = 0;
        for (int i = 0;i < answer.size();i ++){
            int c = answer.get(i);
            if (c < 0 || c > 1) return 0;
            if (c == 0) score += 1;
        }
        return score;
    }

    static public int getDepScore(List<Integer> answer){
        int score = 0;
        for (int i = 0;i < answer.size();i ++){
            int c = answer.get(i);
            if (c < 0 || c > 3) return 0;
            score += depressionScore[i][c];
        }
        return score;
    }

    static public int getMatureScore(List<Integer> answer){
        int score = 0;
        for (int i = 0;i < answer.size();i ++){
            int c = answer.get(i);
            if (c < 0 || c > 4) return 0;
            score += matureScore[i][c];
        }
        return score;
    }

    static public int getSocialScore(List<Integer> answer){
        int score = 0;
        for (int i = 0;i < answer.size();i ++){
            int c = answer.get(i);
            if (c < 0 || c > 1) return 0;
            score += socialScore[i][c];
        }
        return score;
    }
}
