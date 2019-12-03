package top.imlgw.rbac.result;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author imlgw.top
 * @date 2019/12/3 21:45
 */
@Getter
@Setter
@ToString
public class PageResult<T> {

    private List<T> data = new ArrayList<>();

    private int total = 0;
}