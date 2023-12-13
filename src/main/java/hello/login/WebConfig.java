package hello.login;

import hello.login.web.filter.Logfilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebConfig {
    @Bean
    // 필터 등록할때 쓰는 법
    // 부트가 was를 들고 띄우기 때문에 was를 띄울때 필터를 넣어줌
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new Logfilter());
        filterFilterRegistrationBean.setOrder(1);
        filterFilterRegistrationBean.addUrlPatterns("/*"); // 모든 url 적용

        return filterFilterRegistrationBean;
    }
}
