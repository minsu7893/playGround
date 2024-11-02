package com.playGround.mapper;

import com.example.model.Ssh;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface SshMapper {
    // 데이터 조회 (기본 키로 조회)
    @Select("SELECT ssh_accept_time FROM ssh WHERE ssh_accept_time = #{sshAcceptTime}")
    Ssh selectByAcceptTime(Date sshAcceptTime);

    // 데이터 삽입
    @Insert("INSERT INTO ssh (ssh_accept_time) VALUES (#{sshAcceptTime})")
    int insertSsh(Ssh ssh);
}