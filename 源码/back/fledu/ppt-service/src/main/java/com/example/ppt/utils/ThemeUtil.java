package com.example.ppt.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.util.List;

@Data
public class ThemeUtil {
    static final String themeString = "[{\"key\":\"purple\",\"name\":\"紫影幽蓝\",\"thumbnail\":\"https://bjcdn.openstorage.cn/open_res/xfyundoc/2023-12-08/f5406925-fbea-498e-bfb8-af900069e659/1.png\"}," +
            "{\"key\":\"green\",\"name\":\"清新翠绿\",\"thumbnail\":\"https://bjcdn.openstorage.cn/open_res/xfyundoc/2023-12-08/dfea2504-6c84-4e72-8878-9232c7795cbf/2.png\"}," +
            "{\"key\":\"lightblue\",\"name\":\"清逸天蓝 \",\"thumbnail\":\"https://bjcdn.openstorage.cn/open_res/xfyundoc/2023-12-08/5818984f-2f17-4814-84d0-622cc157e4cd/3.png\"}," +
            "{\"key\":\"taupe\",\"name\":\"质感之境\",\"thumbnail\":\"https://bjcdn.openstorage.cn/open_res/xfyundoc/2023-12-08/4c94360b-5b09-41a5-a16d-07a881d0e236/4.png\"}," +
            "{\"key\":\"blue\",\"name\":\"星光夜影\",\"thumbnail\":\"https://bjcdn.openstorage.cn/open_res/xfyundoc/2023-12-08/9bb5f37e-9983-4d47-abbf-0e322e964d85/5.png\"}," +
            "{\"key\":\"telecomRed\",\"name\":\"炽热暖阳\",\"thumbnail\":\"https://bjcdn.openstorage.cn/open_res/xfyundoc/2023-12-08/2477dccf-85b7-48e7-86a1-e1f574c11a36/6.png\"}," +
            "{\"key\":\"telecomGreen\",\"name\":\"幻翠奇旅\",\"thumbnail\":\"https://bjcdn.openstorage.cn/open_res/xfyundoc/2023-12-08/6c77d553-4f04-4ff6-845d-ae6e579bf8b6/7.png\"}," +
            "{\"key\":\"dragon\",\"name\":\"龙韵新禧\",\"thumbnail\":\"https://openres.xfyun.cn/xfyundoc/2024-01-23/6694baab-7e49-4e3f-8d03-eee11c0e3f5a/1705975235721/8.png\"}," +
            "{\"key\":\"bluedream\",\"name\":\"蓝梦白翼\",\"thumbnail\":\"https://openres.xfyun.cn/xfyundoc/2024-01-23/ad3b551d-5482-43d9-95ce-d4a36d1114cc/1705975293375/9.png\"}," +
            "{\"key\":\"deepdark\",\"name\":\"墨韵金章\",\"thumbnail\":\"https://openres.xfyun.cn/xfyundoc/2024-01-22/e79ed3f0-2a9e-4514-b7f6-b526b5e1699d/1705909573847/10.png\"}," +
            "{\"key\":\"starryJourney\",\"name\":\"星辰旅途\",\"thumbnail\":\"http://bjcdn.openstorage.cn/open_res/ysw_%E4%B8%BB%E9%A2%98%E5%8D%A1%E7%89%87%E5%A4%87%E4%BB%BD%2011.png\"}," +
            "{\"key\":\"rippleWaters\",\"name\":\"水波涟漪\",\"thumbnail\":\"http://bjcdn.openstorage.cn/open_res/qu1_%E4%B8%BB%E9%A2%98%E5%8D%A1%E7%89%87%E5%A4%87%E4%BB%BD%2012.png\"}," +
            "{\"key\":\"crystalFrost\",\"name\":\"晶莹霜华\",\"thumbnail\":\"http://bjcdn.openstorage.cn/open_res/cs7_%E4%B8%BB%E9%A2%98%E5%8D%A1%E7%89%87%E5%A4%87%E4%BB%BD%2013.png\"}," +
            "{\"key\":\"sereneSnowMountain\",\"name\":\"雪山静谧\",\"thumbnail\":\"http://bjcdn.openstorage.cn/open_res/le5_%E4%B8%BB%E9%A2%98%E5%8D%A1%E7%89%87%E5%A4%87%E4%BB%BD%2014.png\"}," +
            "{\"key\":\"auroraMiracle\",\"name\":\"极光奇迹\",\"thumbnail\":\"http://bjcdn.openstorage.cn/open_res/u8o_%E4%B8%BB%E9%A2%98%E5%8D%A1%E7%89%87%E5%A4%87%E4%BB%BD%2015.png\"}," +
            "{\"key\":\"dawnMistRising\",\"name\":\"晨雾初升\",\"thumbnail\":\"http://bjcdn.openstorage.cn/open_res/2mh_%E4%B8%BB%E9%A2%98%E5%8D%A1%E7%89%87%E5%A4%87%E4%BB%BD%2016.png\"}," +
            "{\"key\":\"nightSkyMirror\",\"name\":\"夜空之镜\",\"thumbnail\":\"http://bjcdn.openstorage.cn/open_res/k2c_%E4%B8%BB%E9%A2%98%E5%8D%A1%E7%89%87%E5%A4%87%E4%BB%BD%2017.png\"}," +
            "{\"key\":\"whisperingWaves\",\"name\":\"海浪呢喃\",\"thumbnail\":\"http://bjcdn.openstorage.cn/open_res/aiv_%E4%B8%BB%E9%A2%98%E5%8D%A1%E7%89%87%E5%A4%87%E4%BB%BD%2018.png\"}," +
            "{\"key\":\"brocadeDragonSoar\",\"name\":\"锦绣龙腾\",\"thumbnail\":\"http://bjcdn.openstorage.cn/open_res/cym_%E4%B8%BB%E9%A2%98%E5%8D%A1%E7%89%87%E5%A4%87%E4%BB%BD%2019.png\"}," +
            "{\"key\":\"stagePrelude\",\"name\":\"舞台序曲\",\"thumbnail\":\"http://bjcdn.openstorage.cn/open_res/m9b_%E4%B8%BB%E9%A2%98%E5%8D%A1%E7%89%87%E5%A4%87%E4%BB%BD%2020.png\"}]";

    static public JSONArray getThemeList()
    {
        return JSON.parseArray(themeString);
    }

    @Data
    public static class Theme{
        public Theme(String key, String name, String thumbnail)
        {
            this.key = key;
            this.name = name;
            this.thumbnail = thumbnail;
        }
        String key;
        String name;
        String thumbnail;
    }
}
