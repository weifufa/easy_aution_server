package com.weifufa.easyaution.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.Producer;
import com.weifufa.common.constant.MemberConstant;
import com.weifufa.common.utils.RRException;
import com.weifufa.easyaution.member.service.CaptchaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;
@Service
public class CaptchaServiceImpl implements CaptchaService {
    @Autowired
     Producer producer;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Override
    public BufferedImage getCaptcha(String uuid) {
            if(StringUtils.isBlank(uuid)){
                throw new RRException("uuid不能为空");
            }
            //生成文字验证码
            String code = producer.createText();
            //将验证码存入redis中
           redisTemplate.opsForValue().set(uuid,code, MemberConstant.CAPTCHA_EXPIRE, TimeUnit.SECONDS); //设置过期时间
            return producer.createImage(code);

    }

    @Override
    public boolean validate(String uuid, String code) {
        String validCode = redisTemplate.opsForValue().get(uuid);
        if(validCode == null){
            return false;  //不存在过期
        }
        //删除验证码
        redisTemplate.delete(uuid);
        if(validCode.equalsIgnoreCase(code)){
            return true;
        }
        return false;
    }
}