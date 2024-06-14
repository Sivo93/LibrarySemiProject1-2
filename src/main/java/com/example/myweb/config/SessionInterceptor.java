package com.example.myweb.config;

import com.example.myweb.member.dto.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;



/*
사용자가 로그인을 하면 세션에 로그인 정보를 저장
이 인터셉터는 모든 요청마다 세션에 저장된 로그인 정보를 확인하고, 해당 정보를 모델에 추가
모든 페이지에서 공통적으로 로그인 상태를 유지하여 사용자에게 로그인/로그아웃 정보를 표시
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession();
        String loginId = (String) session.getAttribute("loginId");
        if (loginId != null && modelAndView != null) {
            MemberDTO member = (MemberDTO) session.getAttribute("member");
            modelAndView.addObject("member", member);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // After completion logic
    }
}
