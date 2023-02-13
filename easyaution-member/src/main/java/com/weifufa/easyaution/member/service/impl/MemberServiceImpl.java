package com.weifufa.easyaution.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weifufa.common.constant.MemberConstant;
import com.weifufa.common.execption.BizCodeEnume;
import com.weifufa.common.utils.PageUtils;
import com.weifufa.common.utils.Query;
import com.weifufa.common.utils.R;
import com.weifufa.easyaution.member.dao.MemberDao;
import com.weifufa.easyaution.member.entity.MemberEntity;
import com.weifufa.easyaution.member.service.MemberService;
import com.weifufa.easyaution.member.vo.MemberLoginVo;
import com.weifufa.easyaution.member.vo.MemberResVo;
import com.weifufa.easyaution.member.vo.MemberSmsLoginVo;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    MemberDao memberDao;
    /**
     * 登录
     * @param vo
     * @return
     */
    @Override
    public MemberEntity login(MemberLoginVo vo) {
        String username=vo.getUsername();
        String password=vo.getPassword();
        //根据用户名或者手机号或者邮箱登录
        MemberEntity entity=this.baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("username",username).or().eq("phone",username).or().eq("email",username));
        if(entity==null)
        {
            //登录失败
            return null;
        }
        else
        {
            //获取到数据库的password
            String passwordDb=entity.getPassword();
            BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
            //2.密码匹配
            boolean matches=passwordEncoder.matches(password,passwordDb);
            if(matches)
            {
                return entity;
            }else {
                return null;
            }
        }
    }

    @Override
    public MemberEntity isExitPhone(String phone) {
        if(StringUtils.isEmpty(phone))return null;
        return  this.getOne(new QueryWrapper<MemberEntity>().eq("phone", phone));
    }

    @Override
    public R smsLogin(MemberSmsLoginVo vo) {
        MemberEntity entity=isExitPhone(vo.getPhone()); //验证手机是否存在
        if(entity==null)
        {
            return R.error(BizCodeEnume.PHONE_NO_EXIST_EXCEPTION.getCode(), BizCodeEnume.PHONE_NO_EXIST_EXCEPTION.getMsg());
        }
        //验证验证码是否过期
        String redisCode=redisTemplate.opsForValue().get(MemberConstant.SMS_CODE_CACHE_PREFIX+vo.getPhone()); //先去redis中获取验证码
        if(StringUtils.isEmpty(redisCode))//不存在就已经过期
        {
            return R.error(BizCodeEnume.SMS_CODE_EXPIRE.getCode(),BizCodeEnume.SMS_CODE_EXPIRE.getMsg());
        }

        
       //TODO 返回用户基本信息
        return R.ok().setData(entity);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page=this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public R register(MemberResVo vo) {
        MemberDao memberDao = this.baseMapper;
        MemberEntity entity = new MemberEntity();
        //验证验证码是否过期
        String redisCode=redisTemplate.opsForValue().get(MemberConstant.SMS_CODE_CACHE_PREFIX+vo.getPhone()); //先去redis中获取验证码
        if(StringUtils.isEmpty(redisCode))//不存在就已经过期
        {
           return R.error(BizCodeEnume.SMS_CODE_EXPIRE.getCode(),BizCodeEnume.SMS_CODE_EXPIRE.getMsg());
        }

        //判断用户是否存在
        MemberEntity userEntity = this.getOne(new QueryWrapper<MemberEntity>().eq("username", vo.getUsername()));
        if(userEntity!=null)
        {
            return R.error(BizCodeEnume.USER_EXIST_EXCEPTION.getCode(),BizCodeEnume.USER_EXIST_EXCEPTION.getMsg());
        }

        //判断邮箱是否存在
        MemberEntity emailEntity = this.getOne(new QueryWrapper<MemberEntity>().eq("email", vo.getEmail()));
        if(emailEntity!=null)
        {
            return R.error(BizCodeEnume.EMALI_EXIST_EXCEPTION.getCode(),BizCodeEnume.EMALI_EXIST_EXCEPTION.getMsg());
        }
        //盐值加密
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String code=passwordEncoder.encode(vo.getPassword());

        //保存用户信息
        entity.setUsername(vo.getUsername());
        entity.setEmail(vo.getEmail());
        entity.setPassword(code);
        entity.setPhone(vo.getPhone());
        memberDao.insert(entity);
        redisTemplate.delete(MemberConstant.SMS_CODE_CACHE_PREFIX+vo.getPhone());//删除短信验证码
        //返回用户基本信息
        return R.ok().setData(entity);
    }
}