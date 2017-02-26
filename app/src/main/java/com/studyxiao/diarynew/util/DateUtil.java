package com.studyxiao.diarynew.util;

import java.util.Calendar;

/**
 * 关于时间的一切处理
 * Created by Studyxiao on 17/2/22.
 */

public class DateUtil {

    /**
     * 获得系统当前时间,MONTH是0-11,所以要加1
     * @return 如 2017年2月22日 形式。
     */
    public static String getSystemDate(){
        StringBuilder sb = new StringBuilder();
        Calendar now = Calendar.getInstance();
        sb.append(now.get(Calendar.YEAR))
                .append("年")
                .append(now.get(Calendar.MONTH)+1)
                .append("月")
                .append(now.get(Calendar.DAY_OF_MONTH))
                .append("日");
        return sb.toString();
    }

}
