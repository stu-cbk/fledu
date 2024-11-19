package com.example.doc.utils;

import com.example.doc.entity.doc.*;

import java.util.List;

public class TextUtils {

    static public String getAnxText(List<Anxiety> anxieties, List<Integer> options){
        String text = "如果你最近总是感到惶恐、不安，患得患失，无法安心做事情，似乎身后总有一头怪兽追着你跑，那么，测测你的焦虑程度水平，了解自己的焦虑程度，我们需要更加清晰地去觉察自己内在的情绪状态。\n" +
                "本焦虑自评量表 Self-Rating Anxiety Scale（SAS）由华裔教授Zung于1971年编制，含有20个条目，用于评出焦虑的主观感受。\n" +
                "下面请根据您最近一周的感受进行问答。\n";
        int size = options.size();
        for (int i = 0;i < size;i ++)
        {
            Anxiety anxiety = anxieties.get(i);
            String question = anxiety.getQuestion();
            String answer = null;

            int c = options.get(i);
            if (c < 0 || c > 3) continue;
            if (c == 0) answer = anxiety.getA();
            else if (c == 1) answer = anxiety.getB();
            else if (c == 2) answer = anxiety.getC();
            else answer = anxiety.getD();

            String tmp = "问:" + question + " 答:" + answer + "\n";
            text += tmp;
        }
        return text;
    }

    static public String getAloneText(List<Alone> aloneList, List<Integer> options){
        String text = "孤独量表评价由于对社会交往的渴望与实际水平的差距而产生的孤独。本测试含有11 个\"孤独\"正序条目与9 个\" 非孤独”反序条目，" +
                "适用于年满18周岁或以上人士。\n" + "下面请根据您最近一周的感受进行问答。\n";
        int size = options.size();
        for (int i = 0;i < size;i ++)
        {
            Alone alone = aloneList.get(i);
            String question = alone.getQuestion();
            String answer = null;

            int c = options.get(i);
            if (c < 0 || c > 3) continue;
            if (c == 0) answer = alone.getA();
            else if (c == 1) answer = alone.getB();
            else if (c == 2) answer = alone.getC();
            else answer = alone.getD();

            String tmp = "问:" + question + " 答:" + answer + "\n";
            text += tmp;
        }
        return text;
    }

    static public String getDelayText(List<Delay> delayList, List<Integer> options){
        String text = "哪里有尴尬和不舒服，哪里就潜伏着拖延的可能性。战胜拖延的第一步就是了解自己的拖延现状，还等什么，赶快开始自我评估吧！\n"
                + "下面请根据您最近一周的感受进行问答。\n";
        int size = options.size();
        for (int i = 0;i < size;i ++)
        {
            Delay delay = delayList.get(i);
            String question = delay.getQuestion();
            String answer = null;

            int c = options.get(i);
            if (c < 0 || c > 1) continue;
            if (c == 0) answer = delay.getA();
            else answer = delay.getB();

            String tmp = "问:" + question + " 答:" + answer + "\n";
            text += tmp;
        }
        return text;
    }

    static public String getDepText(List<Depression> depressions, List<Integer> options){
        String text = "抑郁自评量表 Self-Rating Depression Scale（SDS）是由美国杜克大学医学院的William W.K.Zung于1965年编制的，" +
                "是目前应用最广泛的抑郁自评量表之一。整个量表共20个条目，用于衡量抑郁状态的轻重程度及其在治疗中的变化，适用于具有抑郁症状的成年人。\n" +
                "下面请根据您最近一周的感受进行问答。\n";
        int size = options.size();
        for (int i = 0;i < size;i ++)
        {
            Depression depression = depressions.get(i);
            String question = depression.getQuestion();
            String answer = null;

            int c = options.get(i);
            if (c < 0 || c > 3) continue;
            if (c == 0) answer = depression.getA();
            else if (c == 1) answer = depression.getB();
            else if (c == 2) answer = depression.getC();
            else answer = depression.getD();

            String tmp = "问:" + question + " 答:" + answer + "\n";
            text += tmp;
        }
        return text;
    }

    static public String getMatureText(List<Mature> matureList, List<Integer> options){
        String text = "处事得当老练通常是个性成熟与否的评定标准，请你根据自己的真实情况，回答以下问题。\n";
        int size = options.size();
        for (int i = 0;i < size;i ++)
        {
            Mature mature = matureList.get(i);
            String question = mature.getQuestion();
            String answer = null;

            int c = options.get(i);
            if (c < 0 || c > 4) continue;
            if (c == 0) answer = mature.getA();
            else if (c == 1) answer = mature.getB();
            else if (c == 2) answer = mature.getC();
            else if (c == 3) answer = mature.getD();
            else answer = mature.getE();

            String tmp = "问:" + question + " 答:" + answer + "\n";
            text += tmp;
        }
        return text;
    }

    static public String getSocialText(List<SocialAvoidance> socialList, List<Integer> options){
        String text = "社交回避及苦恼分别指回避社会交往的倾向及身临其境时的苦恼感受。回避是一种行为表现，苦恼则为情感反应。\n" +
                "请根据您的实际情况进行问答。\n";
        int size = options.size();
        for (int i = 0;i < size;i ++)
        {
            SocialAvoidance socialAvoidance = socialList.get(i);
            String question = socialAvoidance.getQuestion();
            String answer = null;

            int c = options.get(i);
            if (c < 0 || c > 1) continue;
            if (c == 0) answer = socialAvoidance.getA();
            else answer = socialAvoidance.getB();

            String tmp = "问:" + question + " 答:" + answer + "\n";
            text += tmp;
        }
        return text;
    }
}
