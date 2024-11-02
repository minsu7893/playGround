package com.playGround.Service;

import com.playGround.mapper.SshMapper;   // SshMapper import
import com.playGround.model.Ssh;          // Ssh 클래스 import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PlayGroundService {

    private final SshMapper sshMapper;    // SshMapper 필드 선언

    @Autowired
    public PlayGroundService(SshMapper sshMapper) {  // 생성자 주입
        this.sshMapper = sshMapper;
    }

    public String ssh() {
        try {
            // Ssh 객체 생성 및 데이터 설정
            Ssh ssh = new Ssh();
            ssh.setSshAcceptTime(new Date());  // 현재 시간 설정
            sshMapper.insertSsh(ssh);          // 데이터베이스에 삽입

            return "200";
        } catch (Exception e) {
            System.out.println("오류 발생: " + e.getMessage());
            return "";
        }
    }
}
