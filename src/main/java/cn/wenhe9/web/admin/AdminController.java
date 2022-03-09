package cn.wenhe9.web.admin;

import cn.wenhe9.model.pojo.Article;
import cn.wenhe9.model.pojo.Comment;
import cn.wenhe9.model.responseData.ArticleResponseData;
import cn.wenhe9.model.responseData.StaticticsBo;
import cn.wenhe9.service.ArticleService;
import cn.wenhe9.service.SiteService;
import com.github.pagehelper.PageInfo;
import com.sun.net.httpserver.HttpServer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author dujinliang
 * 2021/4/22
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private SiteService siteService;
    @Autowired
    private ArticleService articleService;

    /**
     * 管理中心起始页
     * @param request request域存储数据
     * @return 返回文章 评论 统计 数据
     */
    @GetMapping({"", "/index"})
    public String index(HttpServletRequest request){

        //获得最新的文章 评论 统计
        List<Article> articles = siteService.recentArticles(5);
        List<Comment> comments = siteService.recentComments(5);
        StaticticsBo statistics = siteService.getStatistics();

        //向request域中存储数据
        request.setAttribute("articles", articles);
        request.setAttribute("comments", comments);
        request.setAttribute("statistics", statistics);

        return "back/index";
    }

    /**
     * 像文章发表页面跳转
     * @return 返回文章发表页面
     */
    @GetMapping("/article/toEditPage")
    public String newArticle(){
        return "back/article_edit";
    }

    /**
     * 文章发表
     * @param article 要发表的文章
     * @return 返回发表的状态
     */
    @PostMapping("/article/publish")
    @ResponseBody
    public ArticleResponseData publicArticle(Article article){
        if (StringUtils.isBlank(article.getCategories())){
            article.setCategories("默认分类");
        }
        try {
            articleService.publicArticle(article);
            logger.info("文章发布成功");
            return ArticleResponseData.ok();
        }catch (Exception e){
            logger.error("文章发布失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }

    /**
     * 跳转到文章后台列表页面
     * @param page 当前页数
     * @param count 一页展示的条数
     * @param request request作用域，用来存放数据
     * @return 返回文章列表页面
     */
    @GetMapping("/article")
    public String index(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "count", defaultValue = "10") int count,
                        HttpServletRequest request){
        PageInfo<Article> pageInfo = articleService.queryArticleWithPage(page, count);
        request.setAttribute("articles", pageInfo);
        return "back/article_list";
    }

    /**
     * 跳转到文章修改页面
     * @param id 要修改的文章id
     * @param request request作用于，用于存储数据
     * @return 返回到文章修改页面
     */
    @GetMapping("/article/{id}")
    public String editArticle(@PathVariable("id") String id, HttpServletRequest request){
        Article article = articleService.queryArticleById(Integer.parseInt(id));

        request.setAttribute("contents", article);
        request.setAttribute("categories", article.getCategories());

        return  "back/article_edit";
    }

    @PostMapping("/article/modify")
    @ResponseBody
    public ArticleResponseData modifyArtilce(Article article){
        try {
            articleService.updateArticleById(article);
            logger.info("更新成功");
            return ArticleResponseData.ok();
        }catch (Exception e){
            logger.error("文章更新失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }

    @PostMapping("/article/delete")
    @ResponseBody
    public ArticleResponseData delete(@RequestParam int id){
        try {
            articleService.deleteArticleById(id);
            logger.info("文章删除成功");
            return ArticleResponseData.ok();
        }catch (Exception e){
            logger.error("文章删除失败，错误信息: "+e.getMessage());
            return ArticleResponseData.fail();
        }
    }
}