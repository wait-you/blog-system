package cn.wenhe9.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistic {
    private int id;
    private int articleId;
    private int hits;
    private int commentsNum;
}
