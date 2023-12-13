package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentResolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    // @GetMapping("/")
    public String home() {
        return "home";
    }

    //@GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
        if (memberId == null) return "home";

        // 로그인
        Member loginMember = memberRepository.findById(memberId);

        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";

    }

    //@GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {

        //세션 관리자에 저장된 회원 정보 조회
        Member member = (Member) sessionManager.getSession(request);

        // 로그인
        if (member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";

    }

    //@GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        // 세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "home";
        }

        // 세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginHome";

    }

    // @GetMapping("/")
    // homeLoginV3Spring 메서드는 스프링 MVC 컨트롤러에서 사용되는 메서드로, 로그인한 사용자 정보를 세션에서 가져와서 처리하는 역할을 합니다.
    // @SessionAttribute 어노테이션을 통해 "loginMember"라는 이름의 세션 속성을 가져와서 loginMember 변수에 할당합니다.
    // required = false로 설정되어 있으므로, 세션에 "loginMember" 속성이 없을 경우에도 메서드가 실행됩니다.
    public String homeLoginV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        // 만약 loginMember가 null인 경우, 즉 세션에 로그인한 회원 데이터가 없을 경우에는 "home" 문자열을 반환하여 홈 화면으로 이동합니다.
        if (loginMember == null) {
            return "home";
        }

        // 세션에 로그인한 회원 데이터가 있으면, 해당 회원 데이터를 모델에 추가합니다.
        // 모델은 스프링 MVC에서 뷰에 데이터를 전달하는데 사용되는 객체입니다.
        model.addAttribute("member", loginMember);

        // "loginHome" 문자열을 반환하여 로그인한 상태에서의 홈 화면으로 이동합니다.
        return "loginHome";
    }

    @GetMapping("/")

    // homeLoginV3ArgumentResolver 메서드는 스프링 MVC 컨트롤러에서 사용되는 메서드로, @Login 어노테이션을 통해 로그인한 사용자 정보를 파라미터로 직접 받아와서 처리하는 역할을 합니다.
    // @GetMapping 어노테이션을 통해 "/" 경로에 대한 GET 요청을 처리합니다.
    public String homeLoginV3ArgumentResolver(@Login Member loginMember, Model model) {

        // 만약 loginMember가 null인 경우, 즉 세션에 로그인한 회원 데이터가 없을 경우에는 "home" 문자열을 반환하여 홈 화면으로 이동합니다.
        if (loginMember == null) {
            return "home";
        }

        // 세션에 로그인한 회원 데이터가 있으면, 해당 회원 데이터를 모델에 추가합니다.
        // 모델은 스프링 MVC에서 뷰에 데이터를 전달하는데 사용되는 객체입니다.
        model.addAttribute("member", loginMember);

        // "loginHome" 문자열을 반환하여 로그인한 상태에서의 홈 화면으로 이동합니다.
        return "loginHome";
    }
}