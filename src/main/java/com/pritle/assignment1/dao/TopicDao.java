package com.pritle.assignment1.dao;

import com.pritle.assignment1.domain.Topic;
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
public class TopicDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Topic findTopic(long id) {
        List<Topic> topics = jdbcTemplate.query(
                "SELECT id, description, votes FROM Topic WHERE id = ?",
                (rs, column) -> new Topic(rs.getLong("id"), rs.getString("description"), rs.getInt("votes")),
                id
        );
        return topics.isEmpty() ? null : topics.get(0);
    }

    public void update(Topic topic) {
        jdbcTemplate.update(
                "UPDATE Topic SET description = ? , votes = ? WHERE id = ?",
                topic.getDescription(), topic.getVotes(), topic.getId()
        );
    }
}
