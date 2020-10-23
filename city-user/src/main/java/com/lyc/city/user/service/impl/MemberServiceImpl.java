package com.lyc.city.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyc.city.constant.MemberConstant;
import com.lyc.city.user.dao.MemberDao;
import com.lyc.city.entity.MemberEntity;
import com.lyc.city.user.service.MemberService;
import com.lyc.city.utils.PageUtils;
import com.lyc.city.utils.Query;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

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
     * 封禁用户接口
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

    /**
     * 登录接口
     *
     * @param memberEntity 用户登录信息
     * @return 用户信息
     */
    @Override
    public MemberEntity doLogin(MemberEntity memberEntity) {
        return baseMapper.selectOne(new QueryWrapper<MemberEntity>().lambda()
                .eq(MemberEntity::getPhone, memberEntity.getPhone())
                .eq(MemberEntity::getPassword, memberEntity.getPassword()));
    }

    /**
     * 注册接口
     *
     * @param memberEntity 用户信息
     */
    @Override
    public void doRegister(MemberEntity memberEntity) {
        memberEntity.setCreateTime(new Date());
        baseMapper.insert(memberEntity);
    }

    /**
     * 根据id查询用户类
     *
     * @param memberId 用户id
     * @return 用户类
     */
    @Override
    public MemberEntity getMemberById(Long memberId) {
        return baseMapper.selectById(memberId);
    }

    /**
     * 根据电话查询用户类
     *
     * @param phone 电话号码
     * @return 用户类
     */
    @Override
    public MemberEntity getMemberByPhone(String phone) {
        return baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("phone",phone));
    }

    /**
     * 根据id查询用户名
     *
     * @param memberId 用户id
     * @return 用户名
     */
    @Override
    public MemberEntity getMemberNameById(Long memberId) {
        QueryWrapper<MemberEntity> wrapper = new QueryWrapper<>();
        return baseMapper.selectOne(wrapper.select("name").eq("id",memberId));
    }
}