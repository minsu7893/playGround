package com.playGround.model;

import java.util.Date;

public class Ssh {
    private Date sshAcceptTime;

    public Date getSshAcceptTime() {
        return sshAcceptTime;
    }

    public void setSshAcceptTime(Date sshAcceptTime) {
        this.sshAcceptTime = sshAcceptTime;
    }

    @Override
    public String toString() {
        return "Ssh{" +
                "sshAcceptTime=" + sshAcceptTime +
                '}';
    }
}
