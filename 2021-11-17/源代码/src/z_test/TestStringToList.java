package z_test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xzy
 * @create 2021/11/2 12:20
 */
public class TestStringToList {
    public static void main(String[] args) {
        //字符串转list<String>
        String str = "a,b,c,d";
        //此处为了将字符串中的空格去除做了一下操作
        List<String> list= Arrays.asList(str .split(",")).stream().map(s -> (s.trim())).collect(Collectors.toList());
        //list<String>转字符串（以逗号隔开）
//        System.out.println(String.join(",", list));
        System.out.println(list);
    }
}
