package com.lyc.city.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyc.city.entity.MemberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author lyc
 * @email 708901735@qq.com
 * @date 2020-09-14 15:15:19
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {

    List<MemberEntity> selectAllMembers();

    MemberEntity doLogin(@Param("memberEntity") MemberEntity memberEntity);

    MemberEntity selectMemberById(@Param("memberId") Long memberId);

    MemberEntity selectMemberByPhone(@Param("phone")String phone);

    MemberEntity selectMemberNameById(@Param("memberId") Long memberId);
}
