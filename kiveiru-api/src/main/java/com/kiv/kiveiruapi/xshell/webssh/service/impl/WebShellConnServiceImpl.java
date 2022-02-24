package com.kiv.kiveiruapi.xshell.webssh.service.impl;

import com.kiv.kiveiruapi.xshell.webssh.dao.WebShellConnRepository;
import com.kiv.kiveiruapi.xshell.webssh.entity.WebShellConnEntity;
import com.kiv.kiveiruapi.xshell.webssh.service.WebShellConnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebShellConnServiceImpl implements WebShellConnService {
    @Autowired
    private WebShellConnRepository webShellConnRepository;
    public List<WebShellConnEntity> searchHost(String host){
        return webShellConnRepository.findAllByHostLike("%"+host+"%");
    }

    @Override
    public void deleteHost(Long id) {
        webShellConnRepository.deleteById(id);
    }
}
