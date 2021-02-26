package xyz.kail.demo.mybatis.core.tool;

import org.apache.ibatis.reflection.Reflector;
import xyz.kail.demo.mybatis.core.mapper.HelpKeywordVO;

import java.util.Locale;

/**
 * 对 VO 对象属性进行缓存
 */
public class ReflectorMain {

    public static void main(String[] args) {
        Reflector reflector = new Reflector(HelpKeywordVO.class);

        // 驼峰形式
        String propertyName = reflector.findPropertyName("HELP_KEYWORD_ID".replace("_", ""));
        System.out.println(propertyName);
    }

}
