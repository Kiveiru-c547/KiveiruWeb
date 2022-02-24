package com.kiv.kiveiruapi.xshell.webssh.service;

import com.kiv.kiveiruapi.xshell.webssh.entity.WebShellConnEntity;

import java.util.List;

public interface WebShellConnService {
    List<WebShellConnEntity> searchHost(String host);
    void deleteHost(Long id);
}
