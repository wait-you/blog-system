package cn.wenhe9.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private int id;
    private int articleId;
    private Date created;
    private String ip;
    private String content;
    private String status;
    private String author;
}
