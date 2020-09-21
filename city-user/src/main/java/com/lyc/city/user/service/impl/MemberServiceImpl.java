package com.lyc.city.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.city.constant.FlagConstant;
import com.lyc.city.constant.MemberConstant;
import com.lyc.city.entity.InfoEntity;
import com.lyc.city.user.dao.MemberDao;
import com.lyc.city.entity.MemberEntity;
import com.lyc.city.user.service.MemberService;
import com.lyc.city.utils.PageUtils;
import com.lyc.city.utils.Query;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 获取所有的用户信息
     *
     * @return 用户类列表
     */
    @Override
    public List<MemberEntity> getAllMembers() {
        return baseMapper.selectList(new QueryWrapper<>());
    }

    /**
     * 封禁用户
     *
     * @param memberId 用户id
     */
    @Override
    public void deleteLogicMember(Long memberId) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberId);
        memberEntity.setStatus(MemberConstant.MemberStatusEnum.MEMBER_STATUS_BANNED.getCode());
        baseMapper.updateById(memberEntity);
    }

    @Override
    public MemberEntity doLogin(MemberEntity memberEntity) {
        return baseMapper.selectOne(new QueryWrapper<MemberEntity>().lambda()
                .eq(MemberEntity::getPhone, memberEntity.getPhone())
                .eq(MemberEntity::getPassword, memberEntity.getPassword()));
    }

    @Override
    public void doRegister(MemberEntity memberEntity) {
        memberEntity.setCreateTime(new Date());
        baseMapper.insert(memberEntity);
    }

    @Override
    public MemberEntity getMemberById(Long memberId) {
        return baseMapper.selectById(memberId);
    }
}