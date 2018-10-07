package com.pritle.assignment1.dao;

import com.pritle.assignment1.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * © 2017 Pritle Holding B.V. - All Rights Reserved
 * See LICENSE file or http://pritle.com for further details
 *
 * @author Timofey Tsymlov <dev@pritle.com>
 * @version 01/02/2017
 */
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User findUser(String accessToken) {
        List<User> users = jdbcTemplate.query(
                "SELECT access_token, votes_remain FROM User WHERE access_token = ?",
                (rs, column) -> new User(rs.getString("access_token"), rs.getInt("votes_remain")),
                accessToken
        );
        return users.isEmpty() ? null : users.get(0);
    }

    public void update(User user) {
        jdbcTemplate.update(
                "UPDATE User SET votes_remain = ? WHERE access_token = ?",
                user.getVotesRemain(), user.getAccessToken()
        );
    }
}
