package com.hs.mykuaidi.util;

import java.util.HashMap;

/**
 * Created by 黄晟 on 2017/8/7.
 */

public class KuaiDiNameUtil {
    private static HashMap<String ,String> KUAIDI_NAME = new HashMap<>();

    public static String getName(String pinyin){
        if (KUAIDI_NAME.get(pinyin)!=null) {
            return KUAIDI_NAME.get(pinyin);
        }
        return pinyin;
    }

    public static void init() {
        KUAIDI_NAME.put("huitongkuaidi","百世汇通");
        KUAIDI_NAME.put("debangwuliu","德邦物流");
        KUAIDI_NAME.put("guotongkuaidi","国通快递");
        KUAIDI_NAME.put("huitongkuaidi","汇通快运");
        KUAIDI_NAME.put("jixianda","急先达");
        KUAIDI_NAME.put("kuaijiesudi","快捷速递");
        KUAIDI_NAME.put("quanfengkuaidi","全峰快递");
        KUAIDI_NAME.put("shentong","申通");
        KUAIDI_NAME.put("shunfeng","顺丰");
        KUAIDI_NAME.put("wanxiangwuliu","万象物流");
        KUAIDI_NAME.put("yuantong","圆通速递");
        KUAIDI_NAME.put("yunda","韵达快运");
        KUAIDI_NAME.put("yuntongkuaidi","运通快递");
        KUAIDI_NAME.put("youzhengguonei","邮政国内");
        KUAIDI_NAME.put("zhaijisong","宅急送");
        KUAIDI_NAME.put("zhongtong","中通");
    }


}
