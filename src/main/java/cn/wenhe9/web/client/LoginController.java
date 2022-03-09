package cn.wenhe9.web.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author dujinliang
 * 2021/4/23
 */
@Controller
public class LoginController {
    /**
     * 向登录页面跳转，同时封装原始页面地址
     * @param request request域，用户获取数据
     * @param map 用户封装数据
     * @return 返回登录页面
     */
    @GetMapping("/login")
    public String login(HttpServletRequest request, Map map){
        //分别获取请求头和url中的原始访问路径
        String referer = request.getHeader("referer");
        String url = request.getParameter("url");
        System.out.println("referer : " + referer);
        System.out.println("url : " + url);

        //如果参数url中已经封装了原始页面路径，直接返回该路径
        if (url!=null && !"".equals(url)){
            map.put("url", url);
            //如果请求头本身包含登录，将重定向url设为空， 让后台通过用户角色进行选择跳转
        }else if (referer!=null && referer.contains("/login")){
            map.put("url", "");
        }else {
            //否则的话，就记住请求头中的原始数据
            map.put("url", referer);
        }
        return "comm/login";
    }

    /**
     * 对Security拦截的无权限访问异常处理路径映射
     * @param page 页数
     * @param code id号
     * @return 返回403页面
     */
    @GetMapping(value = "/errorPage/{page}/{code}")
    public String accessExceptionHandler(@PathVariable("page") String page, @PathVariable("code") String code) {
        return page+"/"+code;
    }
}
