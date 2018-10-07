package com.pritle.assignment1.domain;

/**
 * © 2017 Pritle Holding B.V. - All Rights Reserved
 * See LICENSE file or http://pritle.com for further details
 *
 * @author Timofey Tsymlov <dev@pritle.com>
 * @version 01/02/2017
 */
public class User {
    private String accessToken;
    private int votesRemain;

    public User() {
    }

    public User(String accessToken, int votesRemain) {
        this.accessToken = accessToken;
        this.votesRemain = votesRemain;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getVotesRemain() {
        return votesRemain;
    }

    public void setVotesRemain(int votesRemain) {
        this.votesRemain = votesRemain;
    }
}
