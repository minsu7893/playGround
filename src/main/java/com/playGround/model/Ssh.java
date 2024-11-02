package com.playGround.model;

import java.util.Date;

public class Ssh {

    private final SshMapper sshMapper;

    @Autowired
    public SshService(SshMapper sshMapper) {
        this.sshMapper = sshMapper;
    }

    public void insertCurrentTime() {
        Ssh ssh = new Ssh();
        ssh.setSshAcceptTime(new Date());  // 현재 시간을 설정합니다.
        
        int result = sshMapper.insertSsh(ssh);  // 데이터베이스에 삽입합니다.
        
        if (result > 0) {
            return "200";
        } else {
            return "";
        }
    }
}