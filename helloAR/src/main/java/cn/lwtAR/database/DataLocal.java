package cn.lwtAR.database;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cn.lwtAR.R;
import cn.lwtAR.entity.AREntity;
import cn.lwtAR.entity.HelpEntity;
import cn.lwtAR.entity.UserEntity;
import cn.lwtAR.utils.JSONUtils;
import cn.lwtAR.utils.ResourceUtil;

/**
 * 数据
 * 
 * @author 王春龙 2016年3月22日
 */
public class DataLocal {
    public static UserEntity getUserInfo(){
        UserEntity userEntity = new UserEntity();
        userEntity.setName("a");
        userEntity.setPassword("b");
        return userEntity;
    }

    public static List<AREntity> getARData(){
        List<AREntity> list = new ArrayList<AREntity>();

        AREntity arEntity1 = new AREntity();
        arEntity1.setImage(R.drawable.ic_title_ar1);
        arEntity1.setImageBody(R.drawable.ic_body_ar1);
        arEntity1.setTime("8:00-12:00");
        arEntity1.setTitle("《金鸡贺岁》");
        arEntity1.setBody("• 以2017年《丁酉年》邮票为图案 1:1原大制作同步发行 \n" +
                "• 延续2016《丙申年》猴票以合家欢乐和谐美满为主题设计 连续12年的经典传承 \n" +
                "• 中国版图常被比喻为一只雄鸡，鸡对于中国人有着特殊情感，鸡同吉，寓意风调雨顺、五谷丰登。鸡年也被称为中国年！ ");

        AREntity arEntity2 = new AREntity();
        arEntity2.setImage(R.drawable.ic_ar_title2);
        arEntity2.setImageBody(R.drawable.ic_ar_body2);
        arEntity2.setTime("8:00-12:00");
        arEntity2.setTitle("《金色记忆贺卡》");
        arEntity2.setBody("金鸡贺岁明信片，采取回忆的方式，以80年代办年货、90年代看春晚、千禧年拜大年、吉祥年全家福为主题，选取以往令人怀念的春节欢乐活动，回顾不同年代的过年情景，唤醒人们的年代记忆，表达鸡年新春的美好祝福。\n" +
                "明信片应用AR技术，以活动的情景展示，丰富的情感记忆，有趣的互动体验，带给人们难忘的过年记忆，是一份充满祝福的贺岁礼。");

        AREntity arEntity3 = new AREntity();
        arEntity3.setImage(R.drawable.ic_ar_title3);
        arEntity3.setImageBody(R.drawable.ic_ar_body3);
        arEntity3.setTime("8:00-12:00");
        arEntity3.setTitle("《贴五福明信片》");
        arEntity3.setBody("过年要有福，鸡年送五福。五福贺卡，选取传统春节的经典场景，以祝福、贴福、迎福、纳福、全家福为主题，传递喜庆、欢乐的过年气氛。\n" +
                "鸡年贺岁送五福，欢乐有趣过大年。五福贺卡，应用AR技术，鲜活、有趣、喜庆，是一份不可多得的伴手礼。");

        AREntity arEntity4 = new AREntity();
        arEntity4.setImage(R.drawable.ic_ar_title4);
        arEntity4.setImageBody(R.drawable.ic_ar_body4);
        arEntity4.setTime("8:00-12:00");
        arEntity4.setTitle("《五福贺岁大礼包》");
        arEntity4.setBody("汇祝福、贴福、迎福、纳福、全家福五福\n" +
                "五福齐聚，鸿福齐天，“福”满到万家\n" +
                "福万家  万家福");

        AREntity arEntity5 = new AREntity();
        arEntity5.setImage(R.drawable.ic_ar_title5);
        arEntity5.setImageBody(R.drawable.ic_ar_body5);
        arEntity5.setTime("8:00-12:00");
        arEntity5.setTitle("《祝福金砖》");
        arEntity5.setBody("选取唐，宋，明，清五位皇帝御笔福字\n" +
                "为原型。如意蝙蝠纹装饰四角，祥云纹点\n" +
                "缀其间，寓意聚四海福气，吉祥五福");


        list.add(arEntity1);
        list.add(arEntity2);
        list.add(arEntity3);
        list.add(arEntity4);
        list.add(arEntity5);

        return list;
    }

    public static List<HelpEntity> getHelpData(Context context){
        List<HelpEntity> helpEntityList = JSONUtils.fromJsonArray(ResourceUtil.getFromAssets(context, "text/help"), HelpEntity.class);
        return helpEntityList;
    }
}
