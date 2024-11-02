package com.playGround.Service;


import com.playGround.mapper.SshMapper;
import com.playGround.model.Ssh;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PlayGroundService {

    private final SshMapper sshMapper;    // SshMapper 필드 선언

    @Autowired
    public PlayGroundService(SshMapper sshMapper) {
        this.sshMapper = sshMapper;
    }


    public int ssh() {
        try {

            Ssh ssh = new Ssh();
            ssh.setSshAcceptTime(new Date());

            return sshMapper.insert(ssh);
        } catch (Exception e) {
            System.out.println();
            return 0;
        }
    }
}