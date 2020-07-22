package com.prinpedia.backend.daoImpl;

import com.prinpedia.backend.dao.RoleDao;
import com.prinpedia.backend.entity.Role;
import com.prinpedia.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
