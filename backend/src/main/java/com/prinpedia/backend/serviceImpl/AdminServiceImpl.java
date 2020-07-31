package com.prinpedia.backend.serviceImpl;

import com.prinpedia.backend.dao.RoleDao;
import com.prinpedia.backend.dao.UserDao;
import com.prinpedia.backend.entity.Role;
import com.prinpedia.backend.entity.User;
import com.prinpedia.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }

    @Override
    public Boolean grantAdmin(String username) {
        User user = userDao.findByName(username);
        if(user == null) return false;
        List<Role> roleList = user.getRoleList();
        for(Role role : roleList) {
            if(role.getRoleName().equals("ROLE_ADMIN")) return true;
        }
        Role admin = roleDao.findByRoleName("ROLE_ADMIN");
        if(admin == null) { admin = new Role(); admin.setRoleName("ROLE_ADMIN"); }
        roleList.add(admin);
        user.setRoleList(roleList);
        userDao.update(user);
        return true;
    }

    @Override
    public Boolean changeUserAbility(String username, Boolean enabled) {
        User user = userDao.findByName(username);
        if(user == null) return false;
        List<Role> roleList = user.getRoleList();
        for(Role role : roleList) {
            if(role.getRoleName().equals("ROLE_ADMIN")) return false;
        }
        if(enabled == null) return false;
        user.setEnabled(enabled);
        userDao.update(user);
        return true;
    }
}
