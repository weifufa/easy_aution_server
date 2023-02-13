package com.weifufa.easyaution.gateway.gateway.interceptos;

//import com.auth0.jwt.exceptions.AlgorithmMismatchException;
//import com.auth0.jwt.exceptions.SignatureVerificationException;
//import com.auth0.jwt.exceptions.TokenExpiredException;
//import com.weifufa.common.execption.BizCodeEnume;
//import com.weifufa.common.utils.JWTUtil;
//import com.weifufa.common.utils.R;
//import org.springframework.web.servlet.HandlerInterceptor;
////import org.springframework.web.servlet.HandlerInterceptor;
////
////import javax.servlet.http.HttpServletRequest;
////import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.security.SignatureException;
//
//
//public class JWTInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        //获取请求头中的令牌
//        String token = request.getHeader("token");
//        try {
//            JWTUtil.verify(token);//验证令牌
//            return true;//放行请求
//            //   return R.error()
//        } catch (SignatureVerificationException e) {
//            //  return R.error(BizCodeEnume.INVALID_SIGN_EXCEPTION.getCode(),BizCodeEnume.INVALID_SIGN_EXCEPTION.getMsg());
//        } catch (TokenExpiredException e) {
//            e.printStackTrace();
//        } catch (AlgorithmMismatchException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        R error = R.error(BizCodeEnume.INVALID_SIGN_EXCEPTION.getCode(), BizCodeEnume.INVALID_SIGN_EXCEPTION.getMsg());
//        response.getWriter().println(error);
//        response.setContentType("application/json;charset=UTF-8");
//        return false;
//    }
//}