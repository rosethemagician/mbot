package com.rs.mb.enums;

public enum TwitterUsersEnum {
    PAJLADA(1, "pajlada"), FORSEN(2, "forsen"), NYMN(3, "nymnion"), XQC(4, "xqc");

    private final int id;
    private String twitter;

    TwitterUsersEnum(int idCanal, String twitter){
        this.id = idCanal;
        this.twitter = twitter;

    }
    public int getId(){
        return id;
    }

    public String getTwitter(){
        return twitter;
    }
}
