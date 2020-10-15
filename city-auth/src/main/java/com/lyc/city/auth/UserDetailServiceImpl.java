package com.lyc.city.auth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lyc.city.auth.feign.UserFeignService;
import com.lyc.city.entity.AreaEntity;
import com.lyc.city.entity.MemberEntity;
import com.lyc.city.utils.R;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lyc
 * @date 2020/10/12 16:20
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserFeignService userFeignService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();

        // 远程调用userService获取登录信息
        String phone = username;
        R r = userFeignService.getMemberByPhone(phone);
        // r中的data转换为MemberEntity
        MemberEntity data = JSONObject.parseObject(JSON.toJSONString(r.get("data")), MemberEntity.class);

        if(data!=null){
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(data.getMemberLevel().toString());
            grantedAuthorities.add(grantedAuthority);
        }

        return new User(data.getPhone(),new BCryptPasswordEncoder().encode(data.getPassword()),grantedAuthorities);
    }
}
