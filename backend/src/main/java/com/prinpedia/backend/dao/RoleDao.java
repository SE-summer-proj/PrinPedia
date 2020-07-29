package com.prinpedia.backend.dao;

import com.prinpedia.backend.entity.Role;

public interface RoleDao {
    Role findByRoleName(String roleName);
}
