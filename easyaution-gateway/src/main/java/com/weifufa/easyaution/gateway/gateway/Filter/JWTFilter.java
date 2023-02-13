package com.weifufa.easyaution.gateway.gateway.Filter;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.weifufa.common.constant.MemberConstant;
import com.weifufa.common.execption.BizCodeEnume;
import com.weifufa.common.utils.JWTUtil;
import com.weifufa.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JWTFilter implements GlobalFilter, Ordered {
    /**
     * 核心过滤方法：业务处理
     *
     * @param exchange：请求上下文（获取request和response）
     * @param chain：过滤器链（控制程序放行）
     * @return
     */
    @Autowired
    StringRedisTemplate redisTemplate;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取请求头中的令牌
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //1、获取当前的请求连接
        String path = request.getURI().getPath();
        //2、判断，此请求地址是否需要进行token检验，如果不需要，直接放行，进入微服务
         if(false){ //先设置为都不拦截    //if (path.contains("/api/member/register")) {    //包含则需要拦截
            R r = new R();
            //获取请求头参数token
            String token = request.getHeaders().getFirst("Authorization");
            try {
                JWTUtil.verify(token);//验证令牌
                String userPhone = JWTUtil.getUserPhone(token);
                String tokenPhone = redisTemplate.opsForValue().get(MemberConstant.TOKEN_PHONE_PREFIX + userPhone);//根据手机号获取对应的token信息
                if(!StringUtils.isEmpty(tokenPhone)) { //存在redis中有数据说明存在，则放行，不存在那么就不放行
                    chain.filter(exchange);//放行
                }else{
                    r = R.error(BizCodeEnume.TOKEN_INVALID_EXCEPTION.getCode(),BizCodeEnume.TOKEN_INVALID_EXCEPTION.getMsg()); //token无效
                }
            } catch (SignatureVerificationException e) {
                e.printStackTrace();
                r = R.error(BizCodeEnume.INVALID_SIGN_EXCEPTION.getCode(), BizCodeEnume.INVALID_SIGN_EXCEPTION.getMsg()); //无效签名
            } catch (TokenExpiredException e) {
                e.printStackTrace();
                r = R.error(BizCodeEnume.TOKEN_EXPIRE_EXCEPTION.getCode(), BizCodeEnume.TOKEN_EXPIRE_EXCEPTION.getMsg()); //token过期
            } catch (AlgorithmMismatchException e) {
                e.printStackTrace();
                r = R.error(BizCodeEnume.TOKEN_DIFFER_EXCEPTION.getCode(),BizCodeEnume.TOKEN_DIFFER_EXCEPTION.getMsg()); //算法不一致
            } catch (Exception e) {
                e.printStackTrace();
                r = R.error(BizCodeEnume.TOKEN_INVALID_EXCEPTION.getCode(),BizCodeEnume.TOKEN_INVALID_EXCEPTION.getMsg()); //token无效
            }

            //3.3作JSON转换
            byte[] bytes = JSON.toJSONString(r).getBytes(StandardCharsets.UTF_8);
            //3.4调用bufferFactory方法,生成DataBuffer对象
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            //指定编码，否则在浏览器中会中文乱码
            response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
            //4.调用Mono中的just方法,返回要写给前端的JSON数据
            return response.writeWith(Mono.just(buffer));
        } else {
            return chain.filter(exchange);
        }

    }

    @Override
    public int getOrder() {
        return 0;
    }
}



