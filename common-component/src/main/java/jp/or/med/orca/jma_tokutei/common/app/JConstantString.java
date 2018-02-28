// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) ansi

package jp.or.med.orca.jma_tokutei.common.app;

import java.util.*;

public class JConstantString
{

    public JConstantString()
    {
    }

    public static final HashMap getGraphTokuteiKensa()
    {
        graphTokuteikensa = new HashMap();
        graphTokuteikensa.put(codeGraphTokuteiKey[0], codesGraphShintaisokutei);
        graphTokuteikensa.put(codeGraphTokuteiKey[1], codesGraphKekyuSantei);
        graphTokuteikensa.put(codeGraphTokuteiKey[2], codesGraphTaishaNaibunpitu);
        graphTokuteikensa.put(codeGraphTokuteiKey[3], codesGraphTaishaJunkankikei);
        graphTokuteikensa.put(codeGraphTokuteiKey[4], codesGraphNyosan);
        graphTokuteikensa.put(codeGraphTokuteiKey[5], codesGraphKetuatu);
        graphTokuteikensa.put(codeGraphTokuteiKey[6], codesGraphNyokensa);
        graphTokuteikensa.put(codeGraphTokuteiKey[7], codesGraphJinkinou);
        graphTokuteikensa.put(codeGraphTokuteiKey[8], codesGraphKankinou);
        return graphTokuteikensa;
    }

    public static final HashMap getKensahouhou()
    {
        kensahouhouCode = new HashMap();
        // eidt s.inoue 2012/07/13
        kensahouhouCode.put("9N016160100000001","実測");
        kensahouhouCode.put("9N016160200000001","自己測定");
        kensahouhouCode.put("9N016160300000001","自己申告");
        kensahouhouCode.put("9A755000000000001","その他");
        kensahouhouCode.put("9A752000000000001","２回目");
        kensahouhouCode.put("9A751000000000001","１回目");
        kensahouhouCode.put("9A765000000000001","その他");
        kensahouhouCode.put("9A762000000000001","２回目");
        kensahouhouCode.put("9A761000000000001","１回目");
        kensahouhouCode.put("3F050000002327101","可視吸光光度法（コレステロール酸化酵素法)");
        kensahouhouCode.put("3F050000002327201","紫外吸光光度法（コレステロール脱水素酵素法)");
        kensahouhouCode.put("3F050000002399901","その他");
        kensahouhouCode.put("3F015000002327101","可視吸光光度法（酵素比色法・グリセロール消去）");
        kensahouhouCode.put("3F015000002327201","紫外吸光光度法（酵素比色法・グリセロール消去）");
        kensahouhouCode.put("3F015000002399901","その他");
        kensahouhouCode.put("3F070000002327101","可視吸光光度法（直接法（非沈殿法））");
        kensahouhouCode.put("3F070000002327201","紫外吸光光度法（直接法（非沈殿法））");
        kensahouhouCode.put("3F070000002399901","その他");
        kensahouhouCode.put("3F077000002327101","可視吸光光度法（直接法（非沈殿法））");
        kensahouhouCode.put("3F077000002327201","紫外吸光光度法（直接法（非沈殿法））");
        kensahouhouCode.put("3F077000002399901","その他");
        kensahouhouCode.put("3J010000002327101","可視吸光光度法（化学酸化法、酵素法、ジアゾ法)");
        kensahouhouCode.put("3J010000002399901","その他");
        kensahouhouCode.put("3B035000002327201","紫外吸光光度法（JSCC標準化対応法）");
        kensahouhouCode.put("3B035000002399901","その他");
        kensahouhouCode.put("3B045000002327201","紫外吸光光度法（JSCC標準化対応法）");
        kensahouhouCode.put("3B045000002399901","その他");
        kensahouhouCode.put("3B090000002327101","可視吸光光度法（JSCC標準化対応法）");
        kensahouhouCode.put("3B090000002399901","その他");
        kensahouhouCode.put("3B070000002327101","可視吸光光度法（JSCC標準化対応法）");
        kensahouhouCode.put("3B070000002399901","その他");
        kensahouhouCode.put("3C015000002327101","可視吸光光度法（酵素法）");
        kensahouhouCode.put("3C015000002399901","その他");
        kensahouhouCode.put("3C020000002327101","可視吸光光度法（ウリカーゼ・ペルオキシターゼ法）");
        kensahouhouCode.put("3C020000002399901","その他");
        kensahouhouCode.put("3A010000002327101","可視吸光光度法（ビウレット法）");
        kensahouhouCode.put("3A010000002399901","その他");
        kensahouhouCode.put("3A015000002327101","可視吸光光度法（BCG法、ＢＣＰ改良法）");
        kensahouhouCode.put("3A015000002399901","その他");
        kensahouhouCode.put("5C095000002302301","エンザイムイムノアッセイ(EIA)");
        kensahouhouCode.put("5C095000002399901","その他");
        kensahouhouCode.put("3D010000001926101","電位差法（ブドウ糖酸化酵素電極法）");
        kensahouhouCode.put("3D010000002227101","可視吸光光度法（ブドウ糖酸化酵素法）");
        kensahouhouCode.put("3D010000001927201","紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法）");
        kensahouhouCode.put("3D010000001999901","その他");
        kensahouhouCode.put("3D010129901926101","電位差法（ブドウ糖酸化酵素電極法）");
        kensahouhouCode.put("3D010129902227101","可視吸光光度法（ブドウ糖酸化酵素法）");
        kensahouhouCode.put("3D010129901927201","紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法）");
        kensahouhouCode.put("3D010129901999901","その他");

        kensahouhouCode.put("3D045000001906202","免疫学的方法（ラテックス凝集比濁法等）");
        kensahouhouCode.put("3D045000001920402","HPLC(不安定分画除去HPLC法）");
        kensahouhouCode.put("3D045000001927102","酵素法");
        kensahouhouCode.put("3D045000001999902","その他");
        // add s.inoue 2013/01/21
        kensahouhouCode.put("3D046000001906202","免疫学的方法（ラテックス凝集比濁法等）(NGSP）");
        kensahouhouCode.put("3D046000001920402","HPLC(不安定分画除去HPLC法）(NGSP）");
        kensahouhouCode.put("3D046000001927102","酵素法(NGSP）");
        kensahouhouCode.put("3D046000001999902","その他(NGSP）");

        kensahouhouCode.put("1A020000000191111","試験紙法（機械読み取り）");
        kensahouhouCode.put("1A020000000190111","試験紙法（目視法）");
        kensahouhouCode.put("1A010000000191111","試験紙法（機械読み取り）");
        kensahouhouCode.put("1A010000000190111","試験紙法（目視法）");
        kensahouhouCode.put("1A100000000191111","試験紙法（機械読み取り）");
        kensahouhouCode.put("1A100000000190111","試験紙法（目視法）");
        kensahouhouCode.put("1A030000000190301","屈折計法");
        kensahouhouCode.put("1A030000000199901","その他");
        kensahouhouCode.put("2A040000001930102","自動血球算定装置");
        kensahouhouCode.put("2A030000001930101","自動血球算定装置");
        kensahouhouCode.put("2A020000001930101","自動血球算定装置");
        kensahouhouCode.put("2A060000001930101","自動血球算定装置");
        kensahouhouCode.put("2A070000001930101","自動血球算定装置");
        kensahouhouCode.put("2A080000001930101","自動血球算定装置");
        kensahouhouCode.put("2A010000001930101","自動血球算定装置");
        kensahouhouCode.put("2A050000001930101","自動血球算定装置");
        kensahouhouCode.put("9N201000000000011","直接撮影");
        kensahouhouCode.put("9N206160700000011","直接撮影");
        kensahouhouCode.put("9N206160800000049","直接撮影");
        kensahouhouCode.put("9N211161100000049","直接撮影");
        kensahouhouCode.put("9N211161200000049","直接撮影");
        kensahouhouCode.put("9N216000000000011","間接撮影");
        kensahouhouCode.put("9N221160700000011","間接撮影");
        kensahouhouCode.put("9N221160800000049","間接撮影");
        kensahouhouCode.put("9N226161100000049","間接撮影");
        kensahouhouCode.put("9N226161200000049","間接撮影");
        kensahouhouCode.put("3B339000002399811","方法問わず");
        kensahouhouCode.put("1B030000001599811","方法問わず");
        kensahouhouCode.put("5D305000002399811","方法問わず");
        kensahouhouCode.put("5C070000002306201","可視吸光光度法（ラテックス凝集比濁法）");
        kensahouhouCode.put("5C070000002306301","可視吸光光度法（免役比濁法）");
        kensahouhouCode.put("5C070000002399901","その他");
        kensahouhouCode.put("5H010000001910111","試験管法　カラム凝集法");
        kensahouhouCode.put("5H010000001999911","その他");
        kensahouhouCode.put("5H020000001910111","試験管法　カラム凝集法");
        kensahouhouCode.put("5H020000001999911","その他");
        kensahouhouCode.put("5E071000002399811","方法問わず");
        kensahouhouCode.put("5F016141002399811","方法問わず");
        kensahouhouCode.put("5F360149502399811","方法問わず");
        kensahouhouCode.put("5F360149702399811","方法問わず");
        kensahouhouCode.put("5F360150002399811","方法問わず");
        kensahouhouCode.put("5F360145002399811","方法問わず");
        kensahouhouCode.put("2B020000002231157","INR値");
        kensahouhouCode.put("2B030000002231157","INR値");
        kensahouhouCode.put("3A015000000106101","TIA");
        kensahouhouCode.put("3A025000002329201","比濁法");
        kensahouhouCode.put("3A030000002329201","比濁法");
        kensahouhouCode.put("3B010000002327101","1:可視吸光光度法");
        kensahouhouCode.put("3B010000002327201","2:紫外吸光光度法(UV法)");
        kensahouhouCode.put("3B010000002399801","3:方法問わず");
        kensahouhouCode.put("3B050000002327101","1:可視吸光光度法");
        kensahouhouCode.put("3B050000002327201","2:紫外吸光光度法(UV法)");
        kensahouhouCode.put("3B050000002399801","3:方法問わず");
        kensahouhouCode.put("3B110000002327101","1:可視吸光光度法");
        kensahouhouCode.put("3B110000002327201","2:紫外吸光光度法(UV法)");
        kensahouhouCode.put("3B110000002399801","3:方法問わず");
        kensahouhouCode.put("3B135000002327101","1:可視吸光光度法");
        kensahouhouCode.put("3B135000002399801","2:方法問わず");
        kensahouhouCode.put("3B160000000127101","可視吸光光度法");
        kensahouhouCode.put("3B160000002327101","1:可視吸光光度法");
        kensahouhouCode.put("3B160000002399801","2:方法問わず");
        kensahouhouCode.put("3B180000002327101","1:可視吸光光度法");
        kensahouhouCode.put("3B180000002327201","2:紫外吸光光度法(UV法)");
        kensahouhouCode.put("3B180000002399801","3:方法問わず");
        kensahouhouCode.put("3B220000002327101","1:可視吸光光度法");
        kensahouhouCode.put("3B220000002327201","2:紫外吸光光度法(UV法)");
        kensahouhouCode.put("3B220000002388801","3:方法問わず");
        kensahouhouCode.put("3B340000002399801","方法問わず");
        kensahouhouCode.put("3B345000002399801","方法問わず");
        kensahouhouCode.put("3C025000002327101","1:可視吸光光度法");
        kensahouhouCode.put("3C025000002327201","2:紫外吸光光度法(UV法)");
        kensahouhouCode.put("3C025000002399801","3:方法問わず");
        kensahouhouCode.put("3D010100001926101","1：電位差法（ブドウ糖酸化酵素電極法）");
        kensahouhouCode.put("3D010100001927201","3：紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法）");
        kensahouhouCode.put("3D010100001999901","4:その他の手法");
        kensahouhouCode.put("3D010100002227101","2：可視吸光光度法（ブドウ糖酸化酵素法）");
        kensahouhouCode.put("3D010106001926101","1：電位差法（ブドウ糖酸化酵素電極法）");
        kensahouhouCode.put("3D010106001927201","3：紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法）");
        kensahouhouCode.put("3D010106001999901","4:その他の手法");
        kensahouhouCode.put("3D010106002227101","2：可視吸光光度法（ブドウ糖酸化酵素法）");
        kensahouhouCode.put("3D010112001926101","1：電位差法（ブドウ糖酸化酵素電極法）");
        kensahouhouCode.put("3D010112001927201","3：紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法）");
        kensahouhouCode.put("3D010112001999901","4:その他");
        kensahouhouCode.put("3D010112002227101","2：可視吸光光度法（ブドウ糖酸化酵素法）");
        kensahouhouCode.put("3F035000002327101","1:可視吸光光度法");
        kensahouhouCode.put("3F035000002399801","2:方法特定せず");
        kensahouhouCode.put("3F065000002327101","1:可視吸光光度法");
        kensahouhouCode.put("3F065000002327201","2:紫外吸光光度法(UV法)");
        kensahouhouCode.put("3F065000002399801","3:方法問わず");
        kensahouhouCode.put("3F110000002327101","1:可視吸光光度法");
        kensahouhouCode.put("3F110000002399801","2:方法特定せず");
        kensahouhouCode.put("3F130000002306101","1:免疫比濁法(TIA)");
        kensahouhouCode.put("3F130000002329201","2:比濁法");
        kensahouhouCode.put("3F130000002399801","3:方法問わず");
        kensahouhouCode.put("3F180000002399801","免疫比濁法");
        kensahouhouCode.put("3F185000002399801","免疫比濁法");
        kensahouhouCode.put("3F190000002399801","免疫比濁法");
        kensahouhouCode.put("3H010000002326101","電位差法");
        kensahouhouCode.put("3H015000002326101","電位差法");
        kensahouhouCode.put("3H020000002326101","電位差法");
        kensahouhouCode.put("3H030000002327101","可視吸光光度法(OCPC)");
        kensahouhouCode.put("3H040000002327101","1:可視吸光光度法");
        kensahouhouCode.put("3H040000002327201","2:紫外吸光光度法(UV法)");
        kensahouhouCode.put("3I010000002327101","可視吸光光度法");
        kensahouhouCode.put("3J015000002327101","可視吸光光度法");
        kensahouhouCode.put("4A025000002299801","精密測定・方法問わず");
        kensahouhouCode.put("4A055000002399801","精密測定・方法問わず");
        kensahouhouCode.put("4B010000002399801","精密測定・方法問わず");
        kensahouhouCode.put("4B015000002399801","精密測定・方法問わず");
        kensahouhouCode.put("4B035000002399801","精密測定・方法問わず");
        kensahouhouCode.put("5D010000002399801","精密測定・方法問わず");
        kensahouhouCode.put("5D015000002302311","ＲＰＨＡ定性");
        kensahouhouCode.put("5D015000002399801","精密測定・方法問わず");
        kensahouhouCode.put("5D100000002399801","精密測定・方法問わず");
        kensahouhouCode.put("5D120000002399801","精密測定・方法問わず");
        kensahouhouCode.put("5D130000002399801","精密測定・方法問わず");
        kensahouhouCode.put("5E035000002306101","1:免疫比濁法(TIA)");
        kensahouhouCode.put("5E035000002315305","2:毒素中和反応（希釈倍率）");
        kensahouhouCode.put("5E065000000102311","1:エンザイムイムノアッセイ(EIA)");
        kensahouhouCode.put("5E065000002302311","2:エンザイムイムノアッセイ(EIA)");
        kensahouhouCode.put("5G160000002311611","ラテックス凝集反応定性");
        kensahouhouCode.put("1A020106000190111","2:試験紙法（目視法）");
        kensahouhouCode.put("1A020106000191111","1:試験紙法（機械読み取り）");
        kensahouhouCode.put("1A020112000190111","2:試験紙法（目視法）");
        kensahouhouCode.put("1A020112000191111","1:試験紙法（機械読み取り）");
        kensahouhouCode.put("1A035000000190101","化学発色法");
        kensahouhouCode.put("1A040000000190111","化学発色法");
        kensahouhouCode.put("1A055000000190111","化学発色法");
        kensahouhouCode.put("1A060000000190111","化学発色法");
        kensahouhouCode.put("1A065000000190111","化学発色法");
//        kensahouhouCode.put("9N016160100000001", "実測");
//        kensahouhouCode.put("9N016160200000001", "自己測定");
//        kensahouhouCode.put("9N016160300000001", "自己申告");
//        kensahouhouCode.put("3F050000002327101", "可視吸光光度法（コレステロール酸化酵素法）");
//        kensahouhouCode.put("3F050000002327201", "紫外吸光光度法（コレステロール脱水素酵素法）");
//        kensahouhouCode.put("3F050000002399901", "その他");
//        kensahouhouCode.put("3F015000002327101", "可視吸光光度法（酵素比色法・グリセロール消去）");
//        kensahouhouCode.put("3F015000002327201", "紫外吸光光度法（酵素比色法・グリセロール消去）");
//        kensahouhouCode.put("3F015000002399901", "その他");
//        kensahouhouCode.put("3F070000002327101", "可視吸光光度法（直接法（非沈殿法））");
//        kensahouhouCode.put("3F070000002327201", "紫外吸光光度法（直接法（非沈殿法））");
//        kensahouhouCode.put("3F070000002399901", "その他");
//        kensahouhouCode.put("3F077000002327101", "可視吸光光度法（直接法（非沈殿法））");
//        kensahouhouCode.put("3F077000002327201", "紫外吸光光度法（直接法（非沈殿法））");
//        kensahouhouCode.put("3F077000002399901", "その他");
//        kensahouhouCode.put("3J010000002327101", "可視吸光光度法（化学酸化法、酵素法、ジアゾ法）");
//        kensahouhouCode.put("3J010000002399901", "その他");
//        kensahouhouCode.put("3B035000002327201", "紫外吸光光度法（JSCC標準化対応法）");
//        kensahouhouCode.put("3B035000002399901", "その他");
//        kensahouhouCode.put("3B045000002327201", "紫外吸光光度法（JSCC標準化対応法）");
//        kensahouhouCode.put("3B045000002399901", "その他");
//        kensahouhouCode.put("3B090000002327101", "可視吸光光度法（JSCC標準化対応法）");
//        kensahouhouCode.put("3B090000002399901", "その他");
//        kensahouhouCode.put("3B070000002327101", "可視吸光光度法（JSCC標準化対応法）");
//        kensahouhouCode.put("3B070000002399901", "その他");
//        kensahouhouCode.put("3C015000002327101", "可視吸光光度法（酵素法）");
//        kensahouhouCode.put("3C015000002399901", "その他");
//        kensahouhouCode.put("3C020000002327101", "可視吸光光度法（ウリカーゼ・ペルオキシターゼ法）");
//        kensahouhouCode.put("3C020000002399901", "その他");
//        kensahouhouCode.put("3A010000002327101", "可視吸光光度法（ビウレット法）");
//        kensahouhouCode.put("3A010000002399901", "その他");
//        kensahouhouCode.put("3A015000002327101", "可視吸光光度法（BCG法、ＢＣＰ改良法）");
//        kensahouhouCode.put("3A015000002399901", "その他");
//        kensahouhouCode.put("5C095000002302301", "エンザイムイムノアッセイ(EIA)");
//        kensahouhouCode.put("5C095000002399901", "その他");
//        kensahouhouCode.put("3D010000001926101", "電位差法（ブドウ糖酸化酵素電極法）");
//        kensahouhouCode.put("3D010000002227101", "可視吸光光度法（ブドウ糖酸化酵素法）");
//        kensahouhouCode.put("3D010000001927201", "紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法）");
//        kensahouhouCode.put("3D010000001999901", "その他");
//        kensahouhouCode.put("3D010129901926101", "電位差法（ブドウ糖酸化酵素電極法）");
//        kensahouhouCode.put("3D010129902227101", "可視吸光光度法（ブドウ糖酸化酵素法）");
//        kensahouhouCode.put("3D010129901927201", "紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法）");
//        kensahouhouCode.put("3D010129901999901", "その他");
//        kensahouhouCode.put("3D045000001906202", "免疫学的方法（ラテックス凝集比濁法等）");
//        kensahouhouCode.put("3D045000001920402", "HPLC（不安定分画除去HPLC法）");
//        kensahouhouCode.put("3D045000001927102", "酵素法");
//        kensahouhouCode.put("3D045000001999902", "その他");
//        kensahouhouCode.put("1A020000000191111", "試験紙法（機械読み取り）");
//        kensahouhouCode.put("1A020000000190111", "試験紙法（目視法）");
//        kensahouhouCode.put("1A010000000191111", "試験紙法（機械読み取り）");
//        kensahouhouCode.put("1A010000000190111", "試験紙法（目視法）");
//        kensahouhouCode.put("1A100000000191111", "試験紙法（機械読み取り）");
//        kensahouhouCode.put("1A100000000190111", "試験紙法（目視法）");
//        kensahouhouCode.put("1A030000000190301", "屈折計法");
//        kensahouhouCode.put("1A030000000199901", "その他");
//        kensahouhouCode.put("5C070000002306201", "可視吸光光度法（ラテックス凝集比濁法）");
//        kensahouhouCode.put("5C070000002306301", "可視吸光光度法（免役比濁法）");
//        kensahouhouCode.put("5C070000002399901", "その他");
//        kensahouhouCode.put("5H010000001910111", "試験管法（カラム凝集法）");
//        kensahouhouCode.put("5H010000001999911", "その他");
//        kensahouhouCode.put("5H020000001910111", "試験管法（カラム凝集法）");
//        kensahouhouCode.put("5H020000001999911", "その他");
//        kensahouhouCode.put("3A010000002327101", "可視吸光光度法（ビウレット法）");
//        kensahouhouCode.put("3A010000002399901", "その他");
//        kensahouhouCode.put("3B010000002327101", "可視吸光光度法");
//        kensahouhouCode.put("3B010000002327201", "紫外吸光光度法(UV法)");
//        kensahouhouCode.put("3B010000002399801", "方法問わず");
//        kensahouhouCode.put("3B050000002327101", "可視吸光光度法");
//        kensahouhouCode.put("3B050000002327201", "紫外吸光光度法(UV法)");
//        kensahouhouCode.put("3B050000002399801", "方法問わず");
//        kensahouhouCode.put("3B110000002327101", "可視吸光光度法");
//        kensahouhouCode.put("3B110000002327201", "紫外吸光光度法(UV法)");
//        kensahouhouCode.put("3B110000002399801", "方法問わず");
//        kensahouhouCode.put("3B050000002327101", "可視吸光光度法");
//        kensahouhouCode.put("3B050000002327201", "方法問わず");
//        kensahouhouCode.put("3B160000002327101", "可視吸光光度法");
//        kensahouhouCode.put("3B160000002399801", "方法問わず");
//        kensahouhouCode.put("3B180000002327101", "可視吸光光度法");
//        kensahouhouCode.put("3B180000002327201", "紫外吸光光度法(UV法)");
//        kensahouhouCode.put("3B180000002399801", "方法問わず");
//        kensahouhouCode.put("3B220000002327101", "可視吸光光度法");
//        kensahouhouCode.put("3B220000002327201", "紫外吸光光度法(UV法)");
//        kensahouhouCode.put("3B220000002388801", "方法問わず");
//        kensahouhouCode.put("3C025000002327101", "可視吸光光度法");
//        kensahouhouCode.put("3C025000002327201", "紫外吸光光度法(UV法)");
//        kensahouhouCode.put("3C025000002399801", "方法問わず");
//        kensahouhouCode.put("3D010100001926101", "電位差法（ブドウ糖酸化酵素電極法）");
//        kensahouhouCode.put("3D010100002227101", "可視吸光光度法（ブドウ糖酸化酵素法）");
//        kensahouhouCode.put("3D010100001927201", "紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法）");
//        kensahouhouCode.put("3D010100001999901", "その他の手法");
//        kensahouhouCode.put("3D010106001926101", "電位差法（ブドウ糖酸化酵素電極法）");
//        kensahouhouCode.put("3D010106002227101", "可視吸光光度法（ブドウ糖酸化酵素法）");
//        kensahouhouCode.put("3D010106001927201", "紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法）");
//        kensahouhouCode.put("3D010106001999901", "その他の手法");
//        kensahouhouCode.put("3D010112001926101", "電位差法（ブドウ糖酸化酵素電極法）");
//        kensahouhouCode.put("3D010112002227101", "可視吸光光度法（ブドウ糖酸化酵素法）");
//        kensahouhouCode.put("3D010112001927201", "紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法）");
//        kensahouhouCode.put("3D010112001999901", "その他の手法");
//        kensahouhouCode.put("3F035000002327101", "可視吸光光度法");
//        kensahouhouCode.put("3F035000002399801", "方法問わず");
//        kensahouhouCode.put("3F065000002327101", "可視吸光光度法");
//        kensahouhouCode.put("3F065000002327201", "紫外吸光光度法(UV法)");
//        kensahouhouCode.put("3F065000002399801", "方法問わず");
//        kensahouhouCode.put("3F110000002327101", "可視吸光光度法");
//        kensahouhouCode.put("3F110000002399801", "方法問わず");
//        kensahouhouCode.put("3F130000002306101", "免疫比濁法(TIA)");
//        kensahouhouCode.put("3F130000002329201", "比濁法");
//        kensahouhouCode.put("3F130000002399801", "方法問わず");
//        kensahouhouCode.put("3F180000002399801", "免疫比濁法");
//        kensahouhouCode.put("3F185000002399801", "免疫比濁法");
//        kensahouhouCode.put("3F190000002399801", "免疫比濁法");
//        kensahouhouCode.put("3H010000002326101", "電位差法");
//        kensahouhouCode.put("3H015000002326101", "電位差法");
//        kensahouhouCode.put("3H020000002326101", "電位差法");
//        kensahouhouCode.put("3H030000002327101", "可視吸光光度法(OCPC)");
//        kensahouhouCode.put("3H040000002327101", "可視吸光光度法");
//        kensahouhouCode.put("3H040000002327201", "紫外吸光光度法(UV法)");
//        // add s.inoue 2012/07/13
//        kensahouhouCode.put("3B135000002327101", "可視吸光光度法");
//        kensahouhouCode.put("3B135000002399801", "方法問わず");
//        kensahouhouCode.put("3I010000002327101", "可視吸光光度法");
//        kensahouhouCode.put("3J015000002327101", "可視吸光光度法");
//        kensahouhouCode.put("4A025000002299801","精密測定・方法問わず");
//        kensahouhouCode.put("4A055000002399801","精密測定・方法問わず");
//        kensahouhouCode.put("4B010000002399801","精密測定・方法問わず");
//        kensahouhouCode.put("4B015000002399801","精密測定・方法問わず");
//        kensahouhouCode.put("4B035000002399801","精密測定・方法問わず");
//        kensahouhouCode.put("5D010000002399801","精密測定・方法問わず");
//        kensahouhouCode.put("5D015000002302311","ＲＰＨＡ定性");
//        kensahouhouCode.put("5D015000002399801","精密測定・方法問わず");
//        kensahouhouCode.put("5D100000002399801","精密測定・方法問わず");
//        kensahouhouCode.put("5D120000002399801","精密測定・方法問わず");
//        kensahouhouCode.put("5D130000002399801","精密測定・方法問わず");
//        kensahouhouCode.put("5E035000002306101","免疫比濁法(TIA)");
//        kensahouhouCode.put("5E035000002315305","毒素中和反応（希釈倍率）");
//        kensahouhouCode.put("5E065000000102311","エンザイムイムノアッセイ(EIA)");
//        kensahouhouCode.put("5E065000002302311","エンザイムイムノアッセイ(EIA)");
//        kensahouhouCode.put("5G160000002311611","ラテックス凝集反応定性");
//        kensahouhouCode.put("1A020106000190111","試験紙法（目視法）");
//        kensahouhouCode.put("1A020106000191111","試験紙法（機械読み取り）");
//        kensahouhouCode.put("1A020112000190111","試験紙法（目視法）");
//        kensahouhouCode.put("1A020112000191111","試験紙法（機械読み取り）");
//        kensahouhouCode.put("1A035000000190101","化学発色法");
//        kensahouhouCode.put("1A040000000190111","化学発色法");
//        kensahouhouCode.put("1A055000000190111","化学発色法");
//        kensahouhouCode.put("1A060000000190111","化学発色法");
//        kensahouhouCode.put("1A065000000190111","化学発色法");

        return kensahouhouCode;
    }

    public static final HashMap getKensaHouhouMap(String s)
    {
        Object obj = null;
        Object obj1 = null;
        ArrayList arraylist = null;
        HashMap hashmap = new HashMap();
        if(s.equals("9N016160100000001") || s.equals("9N016160200000001") || s.equals("9N016160300000001"))
        {
            String as[] = {
                "復位", "1:実測", "2:自己測定", "3:自己申告"
            };
            arraylist = new ArrayList(Arrays.asList(as));
        } else
        if(s.equals("3F050000002327101") || s.equals("3F050000002327201") || s.equals("3F050000002399901"))
        {
            String as1[] = {
                "総コレステロール", "1:可視吸光光度法（コレステロール酸化酵素法)", "2:紫外吸光光度法（コレステロール脱水素酵素法)", "3:その他"
            };
            arraylist = new ArrayList(Arrays.asList(as1));
        } else
        if(s.equals("3F015000002327101") || s.equals("3F015000002327201") || s.equals("3F015000002399901"))
        {
            String as2[] = {
                "中性脂肪（トリグリセリド）", "1:可視吸光光度法（可視吸光光度法（酵素比色法・グリセロール消去）", "2:紫外吸光光度法（酵素比色法・グリセロール消去）", "3:その他"
            };
            arraylist = new ArrayList(Arrays.asList(as2));
        } else
        if(s.equals("3F070000002327101") || s.equals("3F070000002327201") || s.equals("3F070000002399901"))
        {
            String as3[] = {
                "ＨＤＬコレステロール", "1:可視吸光光度法（直接法（非沈殿法））", "2:紫外吸光光度法（直接法（非沈殿法））", "3:その他"
            };
            arraylist = new ArrayList(Arrays.asList(as3));
        } else
        if(s.equals("3F077000002327101") || s.equals("3F077000002327201") || s.equals("3F077000002399901"))
        {
            String as4[] = {
                "ＬＤＬコレステロール", "1:可視吸光光度法（直接法（非沈殿法））", "2:紫外吸光光度法（直接法（非沈殿法））", "3:その他"
            };
            arraylist = new ArrayList(Arrays.asList(as4));
        } else
        if(s.equals("3J010000002327101") || s.equals("3J010000002399901"))
        {
            String as5[] = {
                "総ビリルビン", "1:可視吸光光度法（化学酸化法、酵素法、ジアゾ法)", "2:その他"
            };
            arraylist = new ArrayList(Arrays.asList(as5));
        } else
        if(s.equals("3B035000002327201") || s.equals("3B035000002399901"))
        {
            String as6[] = {
                "GOT（ＡＳＴ）", "1:紫外吸光光度法（JSCC標準化対応法）", "3:その他"
            };
            arraylist = new ArrayList(Arrays.asList(as6));
        } else
        if(s.equals("3B045000002327201") || s.equals("3B045000002399901"))
        {
            String as7[] = {
                "GPT（ＡＬＴ）", "1:紫外吸光光度法（JSCC標準化対応法）", "2:その他"
            };
            arraylist = new ArrayList(Arrays.asList(as7));
        } else
        if(s.equals("3B090000002327101") || s.equals("3B090000002399901"))
        {
            String as8[] = {
                "γ-GT(γ-GTP)", "1:可視吸光光度法（JSCC標準化対応法）", "2:その他"
            };
            arraylist = new ArrayList(Arrays.asList(as8));
        } else
        if(s.equals("3B070000002327101") || s.equals("3B070000002399901"))
        {
            String as9[] = {
                "ＡＬＰ", "1:可視吸光光度法（JSCC標準化対応法）", "2:その他"
            };
            arraylist = new ArrayList(Arrays.asList(as9));
        } else
        if(s.equals("3B070000002327101") || s.equals("3B070000002399901"))
        {
            String as10[] = {
                "血清クレアチニン", "1:可視吸光光度法（酵素法）", "2:その他"
            };
            arraylist = new ArrayList(Arrays.asList(as10));
        } else
        if(s.equals("3C020000002327101") || s.equals("3C020000002399901"))
        {
            String as11[] = {
                "血清尿酸", "1:可視吸光光度法（ウリカーゼ・ペルオキシターゼ法）", "2:その他"
            };
            arraylist = new ArrayList(Arrays.asList(as11));
        } else
        if(s.equals("3A010000002327101") || s.equals("3A010000002399901"))
        {
            String as12[] = {
                "総蛋白", "1:可視吸光光度法（ビウレット法）", "2:その他"
            };
            arraylist = new ArrayList(Arrays.asList(as12));
        } else
        if(s.equals("3A015000002327101") || s.equals("3A015000002399901"))
        {
            String as13[] = {
                "アルブミン", "1:可視吸光光度法（BCG法、ＢＣＰ改良法）", "2:その他"
            };
            arraylist = new ArrayList(Arrays.asList(as13));
        } else
        if(s.equals("5C095000002302301") || s.equals("5C095000002399901"))
        {
            String as14[] = {
                "血清フェリチン", "1:エンザイムイムノアッセイ(EIA)", "2:その他"
            };
            arraylist = new ArrayList(Arrays.asList(as14));
        } else
        if(s.equals("3D010000001926101") || s.equals("3D010000002227101") || s.equals("3D010000001927201") || s.equals("3D010000001999901"))
        {
            String as15[] = {
                "空腹時血糖", "1:電位差法（ブドウ糖酸化酵素電極法）", "2:可視吸光光度法（ブドウ糖酸化酵素法）", "3:紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法）", "4:その他"
            };
            arraylist = new ArrayList(Arrays.asList(as15));
        } else
        if(s.equals("3D010129901926101") || s.equals("3D010129902227101") || s.equals("3D010129901927201") || s.equals("3D010129901999901"))
        {
            String as16[] = {
                "随時血糖", "1:電位差法（ブドウ糖酸化酵素電極法）", "2:可視吸光光度法（ブドウ糖酸化酵素法）", "3:紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法）", "4:その他"
            };
            arraylist = new ArrayList(Arrays.asList(as16));
        } else
        if(s.equals("3D045000001906202") || s.equals("3D045000001920402") || s.equals("3D045000001927102") || s.equals("3D045000001999902"))
        {
            String as17[] = {
                "HBA1C", "1:免疫学的方法（ラテックス凝集比濁法等）", "2：HPLC(不安定分画除去HPLC法）", "3：酵素法", "4:その他"
            };
            arraylist = new ArrayList(Arrays.asList(as17));
        } else
        // add s.inoue 2013/01/21
        if(s.equals("3D046000001906202") || s.equals("3D046000001920402") || s.equals("3D046000001927102") || s.equals("3D046000001999902"))
        {
            String as17_1[] = {
                "HBA1C", "1:免疫学的方法（ラテックス凝集比濁法等）（NGSP）", "2：HPLC(不安定分画除去HPLC法）（NGSP）", "3：酵素法（NGSP）", "4:その他（NGSP）"
            };
            arraylist = new ArrayList(Arrays.asList(as17_1));
        } else
        if(s.equals("1A020000000191111") || s.equals("1A020000000190111"))
        {
            String as18[] = {
                "尿糖", "1:試験紙法（機械読み取り）", "2:試験紙法（目視法）"
            };
            arraylist = new ArrayList(Arrays.asList(as18));
        } else
        if(s.equals("1A020000000191111") || s.equals("1A020000000190111"))
        {
            String as19[] = {
                "尿蛋白", "1:試験紙法（機械読み取り）", "2:試験紙法（目視法）"
            };
            arraylist = new ArrayList(Arrays.asList(as19));
        } else
        if(s.equals("1A100000000191111") || s.equals("1A100000000190111"))
        {
            String as20[] = {
                "尿潜血", "1:試験紙法（機械読み取り）", "2:試験紙法（目視法）"
            };
            arraylist = new ArrayList(Arrays.asList(as20));
        } else
        if(s.equals("1A100000000191111") || s.equals("1A100000000190111"))
        {
            String as21[] = {
                "比重", "1:屈折計法", "2:その他"
            };
            arraylist = new ArrayList(Arrays.asList(as21));
        } else
        if(s.equals("5C070000002306201") || s.equals("5C070000002306301") || s.equals("5C070000002399901"))
        {
            String as22[] = {
                "ＣＲＰ", "1:可視吸光光度法（ラテックス凝集比濁法）", "2:可視吸光光度法（免役比濁法）", "3:その他"
            };
            arraylist = new ArrayList(Arrays.asList(as22));
        } else
        if(s.equals("5H010000001910111") || s.equals("5H010000001999911"))
        {
            String as23[] = {
                "血液型（ＡＢＯ）", "1:試験管法　カラム凝集法", "2:その他"
            };
            arraylist = new ArrayList(Arrays.asList(as23));
        } else
        if(s.equals("5H020000001910111") || s.equals("5H020000001999911"))
        {
            String as24[] = {
                "血液型（Ｒｈ）", "1:血液型（Ｒｈ）", "2:その他"
            };
            arraylist = new ArrayList(Arrays.asList(as24));
        } else
        if(s.equals("3A010000002327101") || s.equals("3A010000002399901"))
        {
            String as25[] = {
                "総蛋白", "1:可視吸光光度法（ビウレット法）", "2:その他"
            };
            arraylist = new ArrayList(Arrays.asList(as25));
        } else
        if(s.equals("3B010000002327101") || s.equals("3B010000002327201") || s.equals("3B010000002399801"))
        {
            String as26[] = {
                "ＣＫ（ＣＰＫ）", "1:可視吸光光度法", "2:紫外吸光光度法(UV法)", "3:方法問わず"
            };
            arraylist = new ArrayList(Arrays.asList(as26));
        } else
        if(s.equals("3B050000002327101") || s.equals("3B050000002327201") || s.equals("3B050000002399801"))
        {
            String as27[] = {
                "ＬＤＨ", "1:可視吸光光度法", "2:紫外吸光光度法(UV法)", "3:方法問わず"
            };
            arraylist = new ArrayList(Arrays.asList(as27));
        } else
        if(s.equals("3B110000002327101") || s.equals("3B110000002327201") || s.equals("3B110000002399801"))
        {
            String as28[] = {
                "ＬＤＨ", "1:可視吸光光度法", "2:紫外吸光光度法(UV法)", "3:方法問わず"
            };
            arraylist = new ArrayList(Arrays.asList(as28));
        } else
        if(s.equals("3B050000002327101") || s.equals("3B050000002327201"))
        {
            String as29[] = {
                "ＬＡＰ", "1:可視吸光光度法", "2:方法問わず"
            };
            arraylist = new ArrayList(Arrays.asList(as29));
        } else
        if(s.equals("3B160000002327101") || s.equals("3B160000002399801"))
        {
            String as30[] = {
                "血清アミラーゼ", "1:可視吸光光度法", "2:方法問わず"
            };
            arraylist = new ArrayList(Arrays.asList(as30));
        } else
        if(s.equals("3B180000002327101") || s.equals("3B180000002327201") || s.equals("3B180000002399801"))
        {
            String as31[] = {
                "血清リパーゼ", "1:可視吸光光度法", "2:紫外吸光光度法(UV法)", "3:方法問わず"
            };
            arraylist = new ArrayList(Arrays.asList(as31));
        } else
        if(s.equals("3B220000002327101") || s.equals("3B220000002327201") || s.equals("3B220000002388801"))
        {
            String as32[] = {
                "総酸性フォスファターゼ", "1:可視吸光光度法", "2:紫外吸光光度法(UV法)", "3:方法問わず"
            };
            arraylist = new ArrayList(Arrays.asList(as32));
        } else
        if(s.equals("3C025000002327101") || s.equals("3C025000002327201") || s.equals("3C025000002399801"))
        {
            String as33[] = {
                "ＢＵＮ（尿素窒素）", "1:可視吸光光度法", "2:紫外吸光光度法(UV法)", "3:方法問わず"
            };
            arraylist = new ArrayList(Arrays.asList(as33));
        } else
        if(s.equals("3D010100001926101") || s.equals("3D010100002227101") || s.equals("3D010100001927201") || s.equals("3D010100001999901"))
        {
            String as34[] = {
                "負荷前血糖値", "1：電位差法（ブドウ糖酸化酵素電極法）", "2：可視吸光光度法（ブドウ糖酸化酵素法）", "3：紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法）", "4:その他の手法"
            };
            arraylist = new ArrayList(Arrays.asList(as34));
        } else
        if(s.equals("3D010106001926101") || s.equals("3D010106002227101") || s.equals("3D010106001927201") || s.equals("3D010106001999901"))
        {
            String as35[] = {
                "負荷１時間後血糖値", "1：電位差法（ブドウ糖酸化酵素電極法）", "2：可視吸光光度法（ブドウ糖酸化酵素法）", "3：紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法）", "4:その他の手法"
            };
            arraylist = new ArrayList(Arrays.asList(as35));
        } else
        if(s.equals("3D010112001926101") || s.equals("3D010112002227101") || s.equals("3D010112001927201") || s.equals("3D010112001999901"))
        {
            String as36[] = {
                "負荷２時間後血糖値", "1：電位差法（ブドウ糖酸化酵素電極法）", "2：可視吸光光度法（ブドウ糖酸化酵素法）", "3：紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法）", "4:その他の手法"
            };
            arraylist = new ArrayList(Arrays.asList(as36));
        } else
        if(s.equals("3F035000002327101") || s.equals("3F035000002399801"))
        {
            String as37[] = {
                "遊離脂肪酸", "1：可視吸光光度法", "2:方法問わず"
            };
            arraylist = new ArrayList(Arrays.asList(as37));
        } else
        if(s.equals("3F065000002327101") || s.equals("3F065000002327201") || s.equals("3F065000002399801"))
        {
            String as38[] = {
                "遊離型コレステロール", "1:可視吸光光度法", "2:紫外吸光光度法(UV法)", "3:方法問わず"
            };
            arraylist = new ArrayList(Arrays.asList(as38));
        } else
        if(s.equals("3F110000002327101") || s.equals("3F110000002399801"))
        {
            String as39[] = {
                "胆汁酸", "1：可視吸光光度法", "2:方法問わず"
            };
            arraylist = new ArrayList(Arrays.asList(as39));
        } else
        if(s.equals("3F130000002306101") || s.equals("3F130000002329201") || s.equals("3F130000002399801"))
        {
            String as40[] = {
                "ベータリポ蛋白", "1:免疫比濁法(TIA)", "2:比濁法", "3:方法問わず"
            };
            arraylist = new ArrayList(Arrays.asList(as40));
        } else
        if(s.equals("3H040000002327101") || s.equals("3H040000002327201"))
        {
            String as41[] = {
                "血清無機リン", "1:可視吸光光度法", "2:紫外吸光光度法(UV法)"
            };
            arraylist = new ArrayList(Arrays.asList(as41));
        }
        hashmap.put(s, arraylist);
        return hashmap;
    }

    public static final HashMap getKensaHouhouMap()
    {
        kensahouhouSet = new HashMap();
        String as[] = {
            "復位", "1:実測", "2:自己測定", "3:自己申告"
        };
        ArrayList arraylist = new ArrayList(Arrays.asList(as));
        kensahouhouSet.put("9N016160100000001", arraylist);
        String as1[] = {
            "総コレステロール", "1:可視吸光光度法（コレステロール酸化酵素法)", "2:紫外吸光光度法（コレステロール脱水素酵素法)", "3:その他"
        };
        ArrayList arraylist1 = new ArrayList(Arrays.asList(as1));
        kensahouhouSet.put("3F050000002327101", arraylist1);
        String as2[] = {
            "中性脂肪（トリグリセリド）", "1:可視吸光光度法（可視吸光光度法（酵素比色法・グリセロール消去）", "2:紫外吸光光度法（酵素比色法・グリセロール消去）", "3:その他"
        };
        ArrayList arraylist2 = new ArrayList(Arrays.asList(as2));
        kensahouhouSet.put("3F015000002327101", arraylist2);
        String as3[] = {
            "ＨＤＬコレステロール", "1:可視吸光光度法（直接法（非沈殿法））", "2:紫外吸光光度法（直接法（非沈殿法））", "3:その他"
        };
        ArrayList arraylist3 = new ArrayList(Arrays.asList(as3));
        kensahouhouSet.put("3F070000002327101", arraylist3);
        String as4[] = {
            "ＬＤＬコレステロール", "1:可視吸光光度法（直接法（非沈殿法））", "2:紫外吸光光度法（直接法（非沈殿法））", "3:その他"
        };
        ArrayList arraylist4 = new ArrayList(Arrays.asList(as4));
        kensahouhouSet.put("3F077000002327101", arraylist4);
        String as5[] = {
            "総ビリルビン", "1:可視吸光光度法（化学酸化法、酵素法、ジアゾ法)", "2:その他"
        };
        ArrayList arraylist5 = new ArrayList(Arrays.asList(as5));
        kensahouhouSet.put("3J010000002327101", arraylist5);
        String as6[] = {
            "GOT（ＡＳＴ）", "1:紫外吸光光度法（JSCC標準化対応法）", "3:その他"
        };
        ArrayList arraylist6 = new ArrayList(Arrays.asList(as6));
        kensahouhouSet.put("3B035000002327201", arraylist6);
        String as7[] = {
            "GPT（ＡＬＴ）", "1:紫外吸光光度法（JSCC標準化対応法）", "2:その他"
        };
        ArrayList arraylist7 = new ArrayList(Arrays.asList(as7));
        kensahouhouSet.put("3B045000002327201", arraylist7);
        String as8[] = {
            "γ-GT(γ-GTP)", "1:可視吸光光度法（JSCC標準化対応法）", "2:その他"
        };
        ArrayList arraylist8 = new ArrayList(Arrays.asList(as8));
        kensahouhouSet.put("3B090000002327101", arraylist8);
        String as9[] = {
            "ＡＬＰ", "1:可視吸光光度法（JSCC標準化対応法）", "2:その他"
        };
        ArrayList arraylist9 = new ArrayList(Arrays.asList(as9));
        kensahouhouSet.put("3B070000002327101", arraylist9);
        String as10[] = {
            "血清クレアチニン", "1:可視吸光光度法（酵素法）", "2:その他"
        };
        ArrayList arraylist10 = new ArrayList(Arrays.asList(as10));
        kensahouhouSet.put("3C015000002327101", arraylist10);
        String as11[] = {
            "血清尿酸", "1:可視吸光光度法（ウリカーゼ・ペルオキシターゼ法）", "2:その他"
        };
        ArrayList arraylist11 = new ArrayList(Arrays.asList(as11));
        kensahouhouSet.put("3C020000002327101", arraylist11);
        String as12[] = {
            "総蛋白", "1:可視吸光光度法（ビウレット法）", "2:その他"
        };
        ArrayList arraylist12 = new ArrayList(Arrays.asList(as12));
        kensahouhouSet.put("3A010000002327101", arraylist12);
        String as13[] = {
            "アルブミン", "1:可視吸光光度法（BCG法、ＢＣＰ改良法）", "2:その他"
        };
        ArrayList arraylist13 = new ArrayList(Arrays.asList(as12));
        kensahouhouSet.put("3A015000002327101", arraylist13);
        String as14[] = {
            "血清フェリチン", "1:エンザイムイムノアッセイ(EIA)", "2:その他"
        };
        ArrayList arraylist14 = new ArrayList(Arrays.asList(as12));
        kensahouhouSet.put("5C095000002302301", arraylist14);
        String as15[] = {
            "空腹時血糖", "1:電位差法（ブドウ糖酸化酵素電極法）", "2:可視吸光光度法（ブドウ糖酸化酵素法）", "3:紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法）", "4:その他"
        };
        ArrayList arraylist15 = new ArrayList(Arrays.asList(as15));
        kensahouhouSet.put("3D010000001926101", arraylist15);
        String as16[] = {
            "随時血糖", "1:電位差法（ブドウ糖酸化酵素電極法）", "2:可視吸光光度法（ブドウ糖酸化酵素法）", "3:紫外吸光光度法（ヘキソキナーゼ法、グルコキナーゼ法、ブドウ糖脱水素酵素法）", "4:その他"
        };
        ArrayList arraylist16 = new ArrayList(Arrays.asList(as16));
        kensahouhouSet.put("3D010129901926101", arraylist16);
        String as17[] = {
            "HBA1C", "1:免疫学的方法（ラテックス凝集比濁法等）", "2：HPLC(不安定分画除去HPLC法）", "3：酵素法", "4:その他"
        };
        ArrayList arraylist17 = new ArrayList(Arrays.asList(as17));
        kensahouhouSet.put("3D045000001906202", arraylist17);
        // add s.inoue 2013/01/21
        String as17_2[] = {
            "HBA1C", "1:免疫学的方法（ラテックス凝集比濁法等）（NGSP）", "2：HPLC(不安定分画除去HPLC法）（NGSP）", "3：酵素法（NGSP）", "4:その他（NGSP）"
        };
        ArrayList arraylist17_2 = new ArrayList(Arrays.asList(as17_2));
        kensahouhouSet.put("3D046000001906202", arraylist17_2);

        String as18[] = {
            "尿糖", "1:試験紙法（機械読み取り）", "2:試験紙法（目視法）"
        };
        ArrayList arraylist18 = new ArrayList(Arrays.asList(as18));
        kensahouhouSet.put("1A020000000191111", arraylist18);
        String as19[] = {
            "尿蛋白", "1:試験紙法（機械読み取り）", "2:試験紙法（目視法）"
        };
        ArrayList arraylist19 = new ArrayList(Arrays.asList(as19));
        kensahouhouSet.put("1A010000000191111", arraylist19);
        String as20[] = {
            "尿潜血", "1:試験紙法（機械読み取り）", "2:試験紙法（目視法）"
        };
        ArrayList arraylist20 = new ArrayList(Arrays.asList(as19));
        kensahouhouSet.put("1A100000000191111", arraylist20);
        String as21[] = {
            "比重", "1:屈折計法", "2:その他"
        };
        ArrayList arraylist21 = new ArrayList(Arrays.asList(as21));
        kensahouhouSet.put("1A030000000190301", arraylist21);
        String as22[] = {
            "ＣＲＰ", "1:可視吸光光度法（ラテックス凝集比濁法）", "2:可視吸光光度法（免役比濁法）", "3:その他"
        };
        ArrayList arraylist22 = new ArrayList(Arrays.asList(as22));
        kensahouhouSet.put("5C070000002306201", arraylist22);
        String as23[] = {
            "血液型（ＡＢＯ）", "1:試験管法　カラム凝集法", "2:その他"
        };
        ArrayList arraylist23 = new ArrayList(Arrays.asList(as23));
        kensahouhouSet.put("5H010000001910111", arraylist23);
        String as24[] = {
            "血液型（Ｒｈ）", "1:血液型（Ｒｈ）", "2:その他"
        };
        ArrayList arraylist24 = new ArrayList(Arrays.asList(as22));
        kensahouhouSet.put("5H020000001910111", arraylist22);
        String as25[] = {
            "眼底検査（キースワグナー分類）", "眼底検査（シェイエ分類：Ｈ）", "眼底検査（シェイエ分類：Ｓ）", "眼底検査（SCOTT分類)"
        };
        ArrayList arraylist25 = new ArrayList(Arrays.asList(as25));
        kensahouhouSet.put("5H020000001910111", arraylist25);
        return kensahouhouSet;
    }

    public static final String checkDoubleKensaHouhouMap(String as[])
    {
        Object obj = null;
        Object obj1 = null;
        Object obj2 = null;
        HashMap hashmap = new HashMap();
        int i = 0;
        boolean flag = false;
        boolean flag1 = false;
        int j = 0;
        int k = 0;
        int l = 0;
        int i1 = 0;
        int j1 = 0;
        int k1 = 0;
        int l1 = 0;
        int i2 = 0;
        int j2 = 0;
        int k2 = 0;
        int l2 = 0;
        int i3 = 0;
        int j3 = 0;
        int k3 = 0;
        int l3 = 0;
        int i4 = 0;
        int j4 = 0;
        int k4 = 0;
        int l4 = 0;
        int i5 = 0;
        int j5 = 0;
        int k5 = 0;
        int l5 = 0;
        int i6 = 0;
        int j6 = 0;
        int k6 = 0;
        int l6 = 0;
        int i7 = 0;
        int j7 = 0;
        int k7 = 0;
        int l7 = 0;
        int i8 = 0;
        int j8 = 0;
        int k8 = 0;
        int l8 = 0;
        int i9 = 0;
        int j9 = 0;
        int k9 = 0;
        int l9 = 0;
        int i10 = 0;
        int j10 = 0;

        int i11 = 0;

        String s = "";
        String s1 = ",";
        for(int k10 = 0; k10 < as.length; k10++)
        {
            if(as[k10].equals("9N016160100000001") || as[k10].equals("9N016160200000001") || as[k10].equals("9N016160300000001"))
            {
                if(++i == 2)
                    s = (new StringBuilder()).append(s).append("腹囲").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3F050000002327101") || as[k10].equals("3F050000002327201") || as[k10].equals("3F050000002399901"))
            {
                if(++j == 2)
                    s = (new StringBuilder()).append(s).append("総コレステロール").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3F015000002327101") || as[k10].equals("3F015000002327201") || as[k10].equals("3F015000002399901"))
            {
                if(++k == 2)
                    s = (new StringBuilder()).append(s).append("中性脂肪(トリグリセリド)").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3F070000002327101") || as[k10].equals("3F070000002327201") || as[k10].equals("3F070000002399901"))
            {
                if(++l == 2)
                    s = (new StringBuilder()).append(s).append("HDLコレステロール").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3F077000002327101") || as[k10].equals("3F077000002327201") || as[k10].equals("3F077000002399901"))
            {
                if(++i1 == 2)
                    s = (new StringBuilder()).append(s).append("LDL").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3J010000002327101") || as[k10].equals("3J010000002399901"))
            {
                if(++j1 == 2)
                    s = (new StringBuilder()).append(s).append("総ビルビリン").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3B035000002327201") || as[k10].equals("3B035000002399901"))
            {
                if(++k1 == 2)
                    s = (new StringBuilder()).append(s).append("GOT(AST)").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3B045000002327201") || as[k10].equals("3B045000002399901"))
            {
                if(++l1 == 2)
                    s = (new StringBuilder()).append(s).append("GPT(ALT)").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3B090000002327101") || as[k10].equals("3B090000002399901"))
            {
                if(++i2 == 2)
                    s = (new StringBuilder()).append(s).append("γ-GT(γ-GTP)").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3B070000002327101") || as[k10].equals("3B070000002399901"))
            {
                if(++j2 == 2)
                    s = (new StringBuilder()).append(s).append("ALP").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3C015000002327101") || as[k10].equals("3C015000002399901"))
            {
                if(++k2 == 2)
                    s = (new StringBuilder()).append(s).append("血清クレチリン").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3C020000002327101") || as[k10].equals("3C020000002399901"))
            {
                if(++l2 == 2)
                    s = (new StringBuilder()).append(s).append("血清尿酸").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3A010000002327101") || as[k10].equals("3A010000002399901"))
            {
                if(++i3 == 2)
                    s = (new StringBuilder()).append(s).append("総蛋白").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3A015000002327101") || as[k10].equals("3A015000002399901"))
            {
                if(++j3 == 2)
                    s = (new StringBuilder()).append(s).append("アルブミン").append(s1).toString();
                continue;
            }
            if(as[k10].equals("5C095000002302301") || as[k10].equals("5C095000002399901"))
            {
                if(++k3 == 2)
                    s = (new StringBuilder()).append(s).append("血清フェリチリン").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3D010000001926101") || as[k10].equals("3D010000002227101") || as[k10].equals("3D010000001927201") || as[k10].equals("3D010000001999901"))
            {
                if(++l3 == 2)
                    s = (new StringBuilder()).append(s).append("空腹時血糖").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3D010129901926101") || as[k10].equals("3D010129902227101") || as[k10].equals("3D010129901927201") || as[k10].equals("3D010129901999901"))
            {
                if(++i4 == 2)
                    s = (new StringBuilder()).append(s).append("随時血糖").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3D045000001906202") || as[k10].equals("3D045000001920402") || as[k10].equals("3D045000001927102") || as[k10].equals("3D045000001999902"))
            {
                if(++j4 == 2)
                    s = (new StringBuilder()).append(s).append("HBA1C").append(s1).toString();
                continue;
            }
            // add s.inoue 2013/01/21
            if(as[k10].equals("3D046000001906202") || as[k10].equals("3D046000001920402") || as[k10].equals("3D046000001927102") || as[k10].equals("3D046000001999902"))
            {
                if(++i11 == 2)
                    s = (new StringBuilder()).append(s).append("HBA1C").append(s1).toString();
                continue;
            }
            if(as[k10].equals("1A020000000191111") || as[k10].equals("1A020000000190111"))
            {
                if(++k4 == 2)
                    s = (new StringBuilder()).append(s).append("尿糖").append(s1).toString();
                continue;
            }
            if(as[k10].equals("1A010000000191111") || as[k10].equals("1A010000000190111"))
            {
                if(++l4 == 2)
                    s = (new StringBuilder()).append(s).append("尿蛋白").append(s1).toString();
                continue;
            }
            if(as[k10].equals("1A100000000191111") || as[k10].equals("1A100000000190111"))
            {
                if(++i5 == 2)
                    s = (new StringBuilder()).append(s).append("尿潜血").append(s1).toString();
                continue;
            }
            if(as[k10].equals("1A030000000190301") || as[k10].equals("1A030000000199901"))
            {
                if(++j5 == 2)
                    s = (new StringBuilder()).append(s).append("尿比重").append(s1).toString();
                continue;
            }
            if(as[k10].equals("5C070000002306201") || as[k10].equals("5C070000002306301") || as[k10].equals("5C070000002399901"))
            {
                if(++k5 == 2)
                    s = (new StringBuilder()).append(s).append("CRP").append(s1).toString();
                continue;
            }
            if(as[k10].equals("5H010000001910111") || as[k10].equals("5H010000001999911"))
            {
                if(++l5 == 2)
                    s = (new StringBuilder()).append(s).append("血液型(ABO)").append(s1).toString();
                continue;
            }
            if(as[k10].equals("5H020000001910111") || as[k10].equals("5H020000001999911"))
            {
                if(++i6 == 2)
                    s = (new StringBuilder()).append(s).append("血液型(RH)").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3A010000002327101") || as[k10].equals("3A010000002399901"))
            {
                if(++j6 == 2)
                    s = (new StringBuilder()).append(s).append("総蛋白").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3B010000002327101") || as[k10].equals("3B010000002327201") || as[k10].equals("3B010000002399801"))
            {
                if(++k6 == 2)
                    s = (new StringBuilder()).append(s).append("ＣＫ（ＣＰＫ）").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3B050000002327101") || as[k10].equals("3B050000002327201") || as[k10].equals("3B050000002399801"))
            {
                if(++l6 == 2)
                    s = (new StringBuilder()).append(s).append("ＬＤＨ").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3B110000002327101") || as[k10].equals("3B110000002327201") || as[k10].equals("3B110000002399801"))
            {
                if(++i7 == 2)
                    s = (new StringBuilder()).append(s).append("コリンエステラーゼ").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3B050000002327101") || as[k10].equals("3B050000002327201"))
            {
                if(++j7 == 2)
                    s = (new StringBuilder()).append(s).append("ＬＡＰ").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3B160000002327101") || as[k10].equals("3B160000002399801"))
            {
                if(++k7 == 2)
                    s = (new StringBuilder()).append(s).append("血清アミラーゼ").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3B180000002327101") || as[k10].equals("3B180000002327201") || as[k10].equals("3B180000002399801"))
            {
                if(++l7 == 2)
                    s = (new StringBuilder()).append(s).append("血清リパーゼ").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3B220000002327101") || as[k10].equals("3B220000002327201") || as[k10].equals("3B220000002388801"))
            {
                if(++i8 == 2)
                    s = (new StringBuilder()).append(s).append("総酸性フォスファターゼ").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3C025000002327101") || as[k10].equals("3C025000002327201") || as[k10].equals("3C025000002399801"))
            {
                if(++j8 == 2)
                    s = (new StringBuilder()).append(s).append("ＢＵＮ（尿素窒素）").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3D010100001926101") || as[k10].equals("3D010100002227101") || as[k10].equals("3D010100001927201") || as[k10].equals("3D010100001999901"))
            {
                if(++k8 == 2)
                    s = (new StringBuilder()).append(s).append("負荷前血糖値").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3D010106001926101") || as[k10].equals("3D010106002227101") || as[k10].equals("3D010106001927201") || as[k10].equals("3D010106001999901"))
            {
                if(++l8 == 2)
                    s = (new StringBuilder()).append(s).append("負荷１時間後血糖値").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3D010112001926101") || as[k10].equals("3D010112002227101") || as[k10].equals("3D010112001927201") || as[k10].equals("3D010112001999901"))
            {
                if(++i9 == 2)
                    s = (new StringBuilder()).append(s).append("負荷２時間後血糖値").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3F035000002327101") || as[k10].equals("3F035000002399801"))
            {
                if(++j9 == 2)
                    s = (new StringBuilder()).append(s).append("遊離脂肪酸").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3F065000002327101") || as[k10].equals("3F065000002327201") || as[k10].equals("3F065000002399801"))
            {
                if(++k9 == 2)
                    s = (new StringBuilder()).append(s).append("遊離型コレステロール").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3F110000002327101") || as[k10].equals("3F110000002399801"))
            {
                if(++l9 == 2)
                    s = (new StringBuilder()).append(s).append("胆汁酸").append(s1).toString();
                continue;
            }
            if(as[k10].equals("3F130000002306101") || as[k10].equals("3F130000002329201") || as[k10].equals("3F130000002399801"))
            {
                if(++i10 == 2)
                    s = (new StringBuilder()).append(s).append("ベータリポ蛋白").append(s1).toString();
                continue;
            }
            if((as[k10].equals("3H040000002327101") || as[k10].equals("3H040000002327201")) && ++j10 == 2)
                s = (new StringBuilder()).append(s).append("血清無機リン").append(s1).toString();
        }

        if(s.length() > 0)
        {
            int l10 = s.lastIndexOf(s1);
            s = s.substring(0, l10);
        }
        return s;
    }

    public static final String SEIKATU_HYOKA_1 = "9N556000000000011";
    public static final String SEIKATU_HYOKA_2 = "9N561000000000011";
    public static final String SEIKATU_HYOKA_3 = "9N566000000000049";
    public static final String ISHINO_HANDAN = "9N571000000000049";
    public static final String MONSHIN_KINOU_1 = "9N811000000000011";
    public static final String MONSHIN_KINOU_2 = "9N816000000000011";
    public static final String MONSHIN_KINOU_3 = "9N821000000000011";
    public static final String MONSHIN_KINOU_4 = "9N826000000000011";
    public static final String MONSHIN_KINOU_5 = "9N831000000000011";
    public static final String MONSHIN_KINOU_6 = "9N836000000000011";
    public static final String MONSHIN_KINOU_7 = "9N841000000000011";
    public static final String MONSHIN_KINOU_8 = "9N846000000000011";
    public static final String MONSHIN_KINOU_9 = "9N851000000000011";
    public static final String MONSHIN_KINOU_10 = "9N856000000000011";
    public static final String MONSHIN_KINOU_11 = "9N861000000000011";
    public static final String MONSHIN_KINOU_12 = "9N866000000000001";
    public static final String MONSHIN_KINOU_13 = "9N871000000000011";
    public static final String MONSHIN_KINOU_14 = "9N876000000000011";
    public static final String MONSHIN_KINOU_15 = "9N881000000000011";
    public static final String MONSHIN_KINOU_16 = "9N886000000000011";
    public static final String MONSHIN_KINOU_17 = "9N891000000000011";
    public static final String MONSHIN_KINOU_18 = "9N896000000000011";
    public static final String MONSHIN_KINOU_19 = "9N901000000000011";
    public static final String MONSHIN_KINOU_20 = "9N906000000000011";
    public static final String MONSHIN_KINOU_21 = "9N911000000000011";
    public static final String MONSHIN_KINOU_22 = "9N916000000000011";
    public static final String MONSHIN_KINOU_23 = "9N921000000000011";
    public static final String MONSHIN_KINOU_24 = "9N926000000000011";
    public static final String MONSHIN_KINOU_25 = "9N931000000000011";
    public static final String codesSeikatuKinou[] = {
        "9N811000000000011", "9N816000000000011", "9N821000000000011", "9N826000000000011", "9N831000000000011", "9N836000000000011", "9N841000000000011", "9N846000000000011", "9N851000000000011", "9N856000000000011",
        "9N861000000000011", "9N866000000000001", "9N871000000000011", "9N876000000000011", "9N881000000000011", "9N886000000000011", "9N891000000000011", "9N896000000000011", "9N901000000000011", "9N906000000000011",
        "9N911000000000011", "9N916000000000011", "9N921000000000011", "9N926000000000011", "9N931000000000011"
    };
    // 排除 問診必須項目排除 "9N701000000000011", "9N706000000000011", "9N711000000000011", "9N736000000000011"
    public static final String codesSeikatuMonshin[] = {
         "9N701167000000049", "9N701167100000049", "9N706167000000049", "9N706167100000049", "9N711167000000049", "9N711167100000049", "9N716000000000011",
        "9N721000000000011", "9N726000000000011", "9N731000000000011",  "9N741000000000011", "9N746000000000011", "9N751000000000011", "9N756000000000011", "9N761000000000011", "9N766000000000011",
        "9N771000000000011", "9N776000000000011", "9N781000000000011", "9N786000000000011", "9N791000000000011", "9N796000000000011", "9N801000000000011", "9N806000000000011"
        };
// del s.inoue 2012/11/07
//    // 問診必須で追加
//    public static final String codesSeikatuMonshinHisu[] = {
//    	"9N701000000000011","9N706000000000011","9N711000000000011","9N736000000000011"
//    };

    public static final String codesGraphShintaisokutei[] = {
        "9N001000000000001", "9N006000000000001", "9N011000000000001", "9N016160100000001", "9N016160200000001", "9N016160300000001"
    };
    public static final String codesGraphKekyuSantei[] = {
        "2A010000001930101", "2A020000001930101", "2A030000001930101", "2A040000001930102", "2A050000001930101", "3I010000002327101"
    };
    // add s.inoue 2013/01/21
    // JDS → NGSP
//    public static final String codesGraphTaishaNaibunpitu[] = {
//        "1A020000000191111", "1A020000000190111", "3D010000001926101", "3D010000002227101", "3D010000001927201", "3D010000001999901", "3D045000001906202", "3D045000001920402", "3D045000001927102", "3D045000001999902"
//    };
    public static final String codesGraphTaishaNaibunpitu[] = {
        "1A020000000191111", "1A020000000190111", "3D010000001926101", "3D010000002227101", "3D010000001927201", "3D010000001999901", "3D046000001906202", "3D046000001920402", "3D046000001927102", "3D046000001999902",
        // eidt s.inoue 2013/02/13
        "3D045000001906202", "3D045000001920402", "3D045000001927102", "3D045000001999902"
    };

    public static final String codesGraphTaishaJunkankikei[] = {
        "3F015000002327101", "3F015000002327201", "3F015000002399901", "3F070000002327101", "3F070000002327201", "3F070000002399901", "3F077000002327101", "3F077000002327201", "3F077000002399901"
    };
    public static final String codesGraphNyosan[] = {
        "1A035000000190101"
    };
    public static final String codesGraphKetuatu[] = {
        "9A755000000000001", "9A752000000000001", "9A751000000000001", "9A765000000000001", "9A762000000000001", "9A761000000000001"
    };
    public static final String codesGraphNyokensa[] = {
        "1A030000000190301", "1A030000000199901", "1A010000000191111", "1A010000000190111", "1A100000000191111", "1A100000000190111"
    };
    public static final String codesGraphJinkinou[] = {
        "3C025000002327101", "3C025000002327201", "3C025000002399801", "3C015000002327101", "3C015000002399901"
    };
    public static final String codesGraphKankinou[] = {
        "3A010000002327101", "3A010000002399901", "3A016000002327102", "3J010000002327101", "3J010000002399901", "3B035000002327201", "3B035000002399901", "3B045000002327201", "3B045000002399901", "3B070000002327101",
        "3B070000002399901", "3B050000002327101", "3B050000002327201", "3B050000002399801", "3B090000002327101", "3B090000002399901"
    };
    public static final String KENSAHOUHOU_FUKUI1 = "9N016160100000001";
    public static final String KENSAHOUHOU_FUKUI2 = "9N016160200000001";
    public static final String KENSAHOUHOU_FUKUI3 = "9N016160300000001";
    public static final String KENSAHOUHOU_CORESTEROLL1 = "3F050000002327101";
    public static final String KENSAHOUHOU_CORESTEROLL2 = "3F050000002327201";
    public static final String KENSAHOUHOU_CORESTEROLL3 = "3F050000002399901";
    public static final String KENSAHOUHOU_CHUSEISIBO1 = "3F015000002327101";
    public static final String KENSAHOUHOU_CHUSEISIBO2 = "3F015000002327201";
    public static final String KENSAHOUHOU_CHUSEISIBO3 = "3F015000002399901";
    public static final String KENSAHOUHOU_HDL1 = "3F070000002327101";
    public static final String KENSAHOUHOU_HDL2 = "3F070000002327201";
    public static final String KENSAHOUHOU_HDL3 = "3F070000002399901";
    public static final String KENSAHOUHOU_LDL1 = "3F077000002327101";
    public static final String KENSAHOUHOU_LDL2 = "3F077000002327201";
    public static final String KENSAHOUHOU_LDL3 = "3F077000002399901";
    public static final String KENSAHOUHOU_BIRUBIRIN1 = "3J010000002327101";
    public static final String KENSAHOUHOU_BIRUBIRIN2 = "3J010000002399901";
    public static final String KENSAHOUHOU_GOT1 = "3B035000002327201";
    public static final String KENSAHOUHOU_GOT2 = "3B035000002399901";
    public static final String KENSAHOUHOU_GPT1 = "3B045000002327201";
    public static final String KENSAHOUHOU_GPT2 = "3B045000002399901";
    public static final String KENSAHOUHOU_RGT1 = "3B090000002327101";
    public static final String KENSAHOUHOU_RGT2 = "3B090000002399901";
    public static final String KENSAHOUHOU_ALP1 = "3B070000002327101";
    public static final String KENSAHOUHOU_ALP2 = "3B070000002399901";
    public static final String KENSAHOUHOU_CREATIRIN1 = "3C015000002327101";
    public static final String KENSAHOUHOU_CREATIRIN2 = "3C015000002399901";
    public static final String KENSAHOUHOU_NYOSAN1 = "3C020000002327101";
    public static final String KENSAHOUHOU_NYOSAN2 = "3C020000002399901";
    public static final String KENSAHOUHOU_TANPAKU1 = "3A010000002327101";
    public static final String KENSAHOUHOU_TANPAKU2 = "3A010000002399901";
    public static final String KENSAHOUHOU_ARUBUMIN1 = "3A015000002327101";
    public static final String KENSAHOUHOU_ARUBUMIN2 = "3A015000002399901";
    public static final String KENSAHOUHOU_FERITIRIN1 = "5C095000002302301";
    public static final String KENSAHOUHOU_FERITIRIN2 = "5C095000002399901";
    public static final String KENSAHOUHOU_KUFUKUJI1 = "3D010000001926101";
    public static final String KENSAHOUHOU_KUFUKUJI2 = "3D010000002227101";
    public static final String KENSAHOUHOU_KUFUKUJI3 = "3D010000001927201";
    public static final String KENSAHOUHOU_KUFUKUJI4 = "3D010000001999901";
    public static final String KENSAHOUHOU_ZUIJI1 = "3D010129901926101";
    public static final String KENSAHOUHOU_ZUIJI2 = "3D010129902227101";
    public static final String KENSAHOUHOU_ZUIJI3 = "3D010129901927201";
    public static final String KENSAHOUHOU_ZUIJI4 = "3D010129901999901";
    public static final String KENSAHOUHOU_HBA1C1_JDS = "3D045000001906202";
    public static final String KENSAHOUHOU_HBA1C2_JDS = "3D045000001920402";
    public static final String KENSAHOUHOU_HBA1C3_JDS = "3D045000001927102";
    public static final String KENSAHOUHOU_HBA1C4_JDS = "3D045000001999902";
    // add s.inoue 2013/01/21
    public static final String KENSAHOUHOU_HBA1C1_NGSP = "3D046000001906202";
    public static final String KENSAHOUHOU_HBA1C2_NGSP = "3D046000001920402";
    public static final String KENSAHOUHOU_HBA1C3_NGSP = "3D046000001927102";
    public static final String KENSAHOUHOU_HBA1C4_NGSP = "3D046000001999902";

    public static final String KENSAHOUHOU_NYOTO1 = "1A020000000191111";
    public static final String KENSAHOUHOU_NYOTO2 = "1A020000000190111";
    public static final String KENSAHOUHOU_NYOTANPAKU1 = "1A010000000191111";
    public static final String KENSAHOUHOU_NYOTANPAKU2 = "1A010000000190111";
    public static final String KENSAHOUHOU_NYOSENKETU1 = "1A100000000191111";
    public static final String KENSAHOUHOU_NYOSENKETU2 = "1A100000000190111";
    public static final String KENSAHOUHOU_HIJYU1 = "1A030000000190301";
    public static final String KENSAHOUHOU_HIJYU2 = "1A030000000199901";
    public static final String KENSAHOUHOU_CRP1 = "5C070000002306201";
    public static final String KENSAHOUHOU_CRP2 = "5C070000002306301";
    public static final String KENSAHOUHOU_CRP3 = "5C070000002399901";
    public static final String KENSAHOUHOU_ABO1 = "5H010000001910111";
    public static final String KENSAHOUHOU_ABO2 = "5H010000001999911";
    public static final String KENSAHOUHOU_RH1 = "5H020000001910111";
    public static final String KENSAHOUHOU_RH2 = "5H020000001999911";
    public static final String KENSAHOUHOU_FUKA1_KIKAI = "1A020106000191111";
    public static final String KENSAHOUHOU_FUKA1_MOKUSHI = "1A020106000190111";
    public static final String KENSAHOUHOU_FUKA2_KIKAI = "1A020112000191111";
    public static final String KENSAHOUHOU_FUKA2_MOKUSHI = "1A020112000190111";
    public static final String KENSAHOUHOU_SOUTANPAKU_KASHI = "3A010000002327101";
    public static final String KENSAHOUHOU_SOUTANPAKU_SONOTA = "3A010000002399901";
    public static final String KENSAHOUHOU_CK_KASHI = "3B010000002327101";
    public static final String KENSAHOUHOU_CK_UV = "3B010000002327201";
    public static final String KENSAHOUHOU_CK_SONOTA = "3B010000002399801";
    public static final String KENSAHOUHOU_LDH_KASHI = "3B050000002327101";
    public static final String KENSAHOUHOU_LDH_UV = "3B050000002327201";
    public static final String KENSAHOUHOU_LDH_SONOTA = "3B050000002399801";
    public static final String KENSAHOUHOU_CORINTERAZE_KASHI = "3B110000002327101";
    public static final String KENSAHOUHOU_CORINTERAZE_UV = "3B110000002327201";
    public static final String KENSAHOUHOU_CORINTERAZE_SONOTA = "3B110000002399801";
    public static final String KENSAHOUHOU_LAP_KASHI = "3B050000002327101";
    public static final String KENSAHOUHOU_LAP_SONOTA = "3B050000002327201";
    public static final String KENSAHOUHOU_KESEIAMIRAZE_KASHI = "3B160000002327101";
    public static final String KENSAHOUHOU_KESEIAMIRAZE_SONOTA = "3B160000002399801";
    public static final String KENSAHOUHOU_KESEIRIPAZE_KASHI = "3B180000002327101";
    public static final String KENSAHOUHOU_KESEIRIPAZE_UV = "3B180000002327201";
    public static final String KENSAHOUHOU_KESEIRIPAZE_SONOTA = "3B180000002399801";
    public static final String KENSAHOUHOU_SOUSANSEI_KASHI = "3B220000002327101";
    public static final String KENSAHOUHOU_SOUSANSEI_UV = "3B220000002327201";
    public static final String KENSAHOUHOU_SOUSANSEI_SONOTA = "3B220000002388801";
    public static final String KENSAHOUHOU_BUN_KASHI = "3C025000002327101";
    public static final String KENSAHOUHOU_BUN_UV = "3C025000002327201";
    public static final String KENSAHOUHOU_BUN_SONOTA = "3C025000002399801";
    public static final String KENSAHOUHOU_FUKA_DENI = "3D010100001926101";
    public static final String KENSAHOUHOU_FUKA_KASHI = "3D010100002227101";
    public static final String KENSAHOUHOU_FUKA_UV = "3D010100001927201";
    public static final String KENSAHOUHOU_FUKA_SONOTA = "3D010100001999901";
    public static final String KENSAHOUHOU_FUKA1_DENI = "3D010106001926101";
    public static final String KENSAHOUHOU_FUKA1_KASHI = "3D010106002227101";
    public static final String KENSAHOUHOU_FUKA1_UV = "3D010106001927201";
    public static final String KENSAHOUHOU_FUKA1_SONOTA = "3D010106001999901";
    public static final String KENSAHOUHOU_FUKA2_DENI = "3D010112001926101";
    public static final String KENSAHOUHOU_FUKA2_KASHI = "3D010112002227101";
    public static final String KENSAHOUHOU_FUKA2_UV = "3D010112001927201";
    public static final String KENSAHOUHOU_FUKA2_SONOTA = "3D010112001999901";
    public static final String KENSAHOUHOU_YURI_KASHI = "3F035000002327101";
    public static final String KENSAHOUHOU_YURI_SONOTA = "3F035000002399801";
    public static final String KENSAHOUHOU_YURICORESTEROL_KASHI = "3F065000002327101";
    public static final String KENSAHOUHOU_YURICORESTEROL_UV = "3F065000002327201";
    public static final String KENSAHOUHOU_YURICORESTEROL_SONOTA = "3F065000002399801";
    public static final String KENSAHOUHOU_TANJYU_KASHI = "3F110000002327101";
    public static final String KENSAHOUHOU_TANJYU_SONOTA = "3F110000002399801";
    public static final String KENSAHOUHOU_BETAROPO_KASHI = "3F130000002306101";
    public static final String KENSAHOUHOU_BETAROPO_UV = "3F130000002329201";
    public static final String KENSAHOUHOU_BETAROPO_SONOTA = "3F130000002399801";
    public static final String KENSAHOUHOU_KESEIMUKIRIN_KASHI = "3H040000002327101";
    public static final String KENSAHOUHOU_KESEIMUKIRIN_SONOTA = "3H040000002327201";
    public static HashMap kensahouhouCode = null;
    public static HashMap graphTokuteikensa = null;
    public static HashMap kensahouhouSet = null;
    public static final String COMBO_METABO_CODE = "9N501000000000011";
    public static final String COMBO_HOKENSIDO_CODE = "9N506000000000011";
    public static final String COLUMN_NAME_HISU_FLG = "HISU_FLG";
    public static final String COLUMN_NAME_JYOUGEN = "JYOUGEN";
    public static final String COLUMN_NAME_KAGEN = "KAGEN";
    public static final String COLUMN_NAME_KOMENTO = "KOMENTO";
    public static final String COLUMN_NAME_HANTEI = "HANTEI";
    public static final String COLUMN_NAME_H_L = "H_L";
    public static final String COLUMN_NAME_JISI_KBN = "JISI_KBN";
    public static final String COLUMN_NAME_JS_JYOUGEN = "JS_JYOUGEN";
    public static final String COLUMN_NAME_JS_KAGEN = "JS_KAGEN";
    public static final String COLUMN_NAME_DS_JYOUGEN = "DS_JYOUGEN";
    public static final String COLUMN_NAME_DS_KAGEN = "DS_KAGEN";
    public static final String COLUMN_NAME_CODE_NUM = "CODE_NUM";
    public static final String COLUMN_NAME_CODE_NAME = "CODE_NAME";
    public static final String COLUMN_NAME_KEKA_TI = "KEKA_TI";
    public static final String COLUMN_NAME_MAX_BYTE_LENGTH = "MAX_BYTE_LENGTH";
    public static final String COLUMN_NAME_DATA_TYPE = "DATA_TYPE";
    public static final String COLUMN_NAME_KENSA_HOUHOU = "KENSA_HOUHOU";
    public static final String COLUMN_NAME_KOUMOKUNAME = "KOUMOKUNAME";
    public static final String COLUMN_NAME_KOUMOKUCD = "KOUMOKUCD";
    public static final String COLUMN_NAME_TANI = "TANI";
    public static final String COLUMN_NAME_HIS_TI = "HIS_TI";
    public static final String CODE_MONSHIN_HEADER = "9N7";
    public static final String CODE_SEIKATU_KAIZEN = "9N801000000000011";
    public static final String CODE_HOKEN_SHIDOU = "9N806000000000011";
    public static final String CODE_SAIKETSU_ZIKAN = "9N141000000000011";
    public static final String CODE_ISHINOHANDAN_1 = "9N501000000000011";
    public static final String CODE_ISHINOHANDAN_2 = "9N506000000000011";
    public static final String CODE_ISHINOHANDAN_3 = "9N511000000000049";
    public static final String CODE_ISHINOHANDAN_4 = "9N516000000000049";
    public static final String codeGraphTokuteiKey[] = {
        "身体測定", "血球算定", "代謝(内分泌系)", "代謝(循環器系)", "代謝(尿酸)", "血圧", "尿検査", "腎機能", "肝機能"
    };

}
