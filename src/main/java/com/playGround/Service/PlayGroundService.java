package com.playGround.Service;


import com.playGround.mapper.SSHMapper;
import com.playGround.model.SSH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PlayGroundService {

    private final SSHMapper sshMapper;    // SshMapper 필드 선언

    @Autowired
    public PlayGroundService(SSHMapper sshMapper) {
        this.sshMapper = sshMapper;
    }


    public int ssh() {
        try {

            SSH ssh = new SSH();
            ssh.setSshAcceptTime(new Date());

            return sshMapper.insert(ssh);
        } catch (Exception e) {
            System.out.println();
            return 0;
        }
    }
}