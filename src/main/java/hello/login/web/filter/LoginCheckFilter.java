package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whileList = {"/", "/members/add", "/login", "/logout", "/css/*"}; // 로그인 안된 사용자도 들어올수 있는 페이지

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request; // httpservlerrequest로 다운 캐스팅 (쓸게 다 거기에 있음)
        String requestURI = httpRequest.getRequestURI(); // 경로 받기

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("인증 체크 필터 시작 {}", requestURI);
            if (isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {}", requestURI);
                HttpSession session = httpRequest.getSession(false);// 세션확인
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    log.info("미인증 사용자 요청 {}", requestURI);
                    // 로그인으로 redirect 하고 로그인 성공시 requestURI(못들어간 현재 페이지)로 다시 돌아가기
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return; // 여기가 중요!! 미인증 사용자는 다음으로 진행하지 않고 그냥 끝!!!!!
                }
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e; // 예외 로깅 가능하지만 톰캣까지 예외를 보내줘야함
        } finally {
            log.info("인증 체크 필터 종료 {} ", requestURI);
        }
    }

    // 화이트 리스트의 경우 인증 체크 X
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whileList, requestURI); // 화이트 리스트에 없는 애들은 인증
    }
}
