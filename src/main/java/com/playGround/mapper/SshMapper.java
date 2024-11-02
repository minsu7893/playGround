package com.playGround.mapper;

import com.playGround.model.Ssh;  // 올바른 패키지 경로로 수정
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface SshMapper {
    // 데이터 조회 (기본 키로 조회)
    @Select("SELECT ssh_accept_time FROM ssh WHERE ssh_accept_time = #{sshAcceptTime}")
    Ssh selectByAcceptTime(java.util.Date sshAcceptTime);  // java.util.Date로 명시

    // 데이터 삽입
    @Insert("INSERT INTO ssh (ssh_accept_time) VALUES (#{sshAcceptTime})")
    int insertSsh(Ssh ssh);
} 