package cn.wenhe9.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private Integer id;
    private String title;
    private String content;
    private Date created;
    private Date modified;
    private String categories;
    private String tags;
    private Boolean allowComment;
    private Boolean allow_comment;
    private String thumbnail;

    private int hits;
    private int commentsNum;
}
