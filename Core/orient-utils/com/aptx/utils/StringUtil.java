package com.aptx.utils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class StringUtil {
    public static final Splitter WHITESPACES_SPLITTER = Splitter.onPattern("\\s+").omitEmptyStrings();
    public static final Splitter COMMA_SPLITER = Splitter.on(',').trimResults().omitEmptyStrings();
    public static final Joiner SPACE_JOINER = Joiner.on(' ').skipNulls();
    public static final Joiner COMMA_JOINER = Joiner.on(',').skipNulls();
    public static final Joiner TAB_JOINER = Joiner.on('\t').skipNulls();

    /***************************** Regular Expression *****************************/
    public static boolean matches(String str, String regex) {
        return Pattern.matches(regex, str);
    }

    public static Collection<String> getMatchGroups(String str, String regex) {
        List<String> retList = Lists.newLinkedList();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while(matcher.find()) {
            retList.add(matcher.group());
        }
        return retList;
    }

    public static String replaceBy(String str, String regex, ReplaceCallback callback) {
        StringBuffer buffer = new StringBuffer();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while(matcher.find()) {
            String group = matcher.group();
            String replace = callback.handle(group);
            matcher.appendReplacement(buffer, replace);
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    public interface ReplaceCallback {
        String handle(String group);
    }
}
