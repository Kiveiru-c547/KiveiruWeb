package com.kiv.kiveiruapi.xshell.webssh.dao;

import com.kiv.kiveiruapi.xshell.webssh.entity.WebShellConnEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebShellConnRepository extends JpaRepository<WebShellConnEntity,Long> {
    WebShellConnEntity findFirstByHostAndUserName(String host,String userName);
    List<WebShellConnEntity> findAllByHostLike(String host);
}
