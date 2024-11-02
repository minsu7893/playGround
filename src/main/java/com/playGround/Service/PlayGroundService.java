package com.playGround.Service;

public class PlayGroundService {

    public String ssh(){

        try{

            // Ssh 객체 생성
            Ssh ssh = new Ssh();
            ssh.setSshAcceptTime(new java.util.Date());  // 필요한 데이터 설정
            sshMapper.insertSsh(ssh);  // 데이터베이스에 삽입

            return "200";

        }catch (Exception e){
            return "";
        }
    }

}
