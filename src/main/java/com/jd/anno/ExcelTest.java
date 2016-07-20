package com.jd.anno;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Created by caozhifei on 2016/7/20.
 */
public class ExcelTest {
    public static void main(String[] args) {
        ExcelVo vo = new ExcelVo();
        vo.setCreated(new Date());
        vo.setUid(10001l);
        vo.setName("jerry");
        vo.setId(11);
        Field[] fields = vo.getClass().getDeclaredFields();
        for(Field filed : fields) {
            if(filed.isAnnotationPresent(Excel.class)){
                System.out.println(filed.getName() + "需要导出到excel");
                Excel excel = filed.getAnnotation(Excel.class);
                System.out.println(filed.getName() + "=" +excel.columnName() +"="+excel.dateFormat());
            }else{
                System.out.println(filed.getName() + "不需要导出到excel");
            }

        }
    }
}
