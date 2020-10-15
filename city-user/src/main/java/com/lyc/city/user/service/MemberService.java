package com.lyc.city.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyc.city.entity.MemberEntity;
import com.lyc.city.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-09-14 15:15:19
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<MemberEntity> getAllMembers();

    void deleteLogicMember(Long memberId);

    MemberEntity doLogin(MemberEntity memberEntity);

    void doRegister(MemberEntity memberEntity);

    MemberEntity getMemberById(Long memberId);

    MemberEntity getMemberByPhone(String phone);
}

