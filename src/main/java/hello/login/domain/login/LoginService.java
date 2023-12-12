package hello.login.domain.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final MemberRepository memberRepository;

    // @return null 이면 로그인 실패
    public Member login(String loginId, String password) {
/*        Optional<Member> findMemberOptional = memberRepository.findByLoginId(loginId);
        Member member = findMemberOptional.get();
        if (member.getPassword().equals(password)) return member;
        else return null;*/

        // MemberRepository를 사용하여 loginId로 멤버를 찾습니다.
        // 찾은 멤버의 비밀번호가 입력한 비밀번호와 일치하는지 확인합니다.
        // 일치하면 해당 멤버를 반환하고, 그렇지 않으면 null을 반환합니다.
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
