package util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xzy
 * @create 2021/11/2 12:22
 */
public class StringToList {
    public List init(String str){
        return Arrays.asList(str .split(",")).stream().map(s -> (s.trim())).collect(Collectors.toList());
    }
}
