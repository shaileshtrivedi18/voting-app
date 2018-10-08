package com.pritle.assignment1.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.pritle.assignment1.domain.Topic;

/**
 * © 2017 Pritle Holding B.V. - All Rights Reserved
 * See LICENSE file or http://pritle.com for further details
 *
 * @author Timofey Tsymlov <dev@pritle.com>
 * @version 01/02/2017
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TopicControllerTest {

    @LocalServerPort
    private int port;

    private String topicUrl;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        topicUrl = "http://localhost:" + port + "/topic/{topicId}";
    }

    @Ignore
    public void topicVotingTest() {

        // values from init-db.sql:
        final long topicIdToVote = 1;
        final String user1Token = "user-1";
        final String user2Token = "user-2";
        final int amountOfVotesPerUser = 30;

        for (int votesRemain = amountOfVotesPerUser; votesRemain > 0; votesRemain--) {
            assertThat(
                    voteForTopic(user1Token, topicIdToVote),
                    equalTo(HttpStatus.OK)
            );
            assertThat(
                    voteForTopic(user2Token, topicIdToVote),
                    equalTo(HttpStatus.OK)
            );
        }

        assertThat(
                "Topic # 1 has 60 votes now (two users, each voted 30 times)",
                getTopic(1).getVotes(), equalTo(60)
        );

        assertThat(
                "user-1 can't vote anymore",
                voteForTopic(user1Token, topicIdToVote),
                equalTo(HttpStatus.METHOD_NOT_ALLOWED)
        );

        assertThat(
                "user-2 can't vote anymore",
                voteForTopic(user2Token, topicIdToVote),
                equalTo(HttpStatus.METHOD_NOT_ALLOWED)
        );
    }
    
    /**
     * test case to vote for a topic in parallel by two users;
     */
    @Test
    public void topicVotingForUsersInParallelTest() {
    	// values from init-db.sql:
        final long topicIdToVote = 1;
        final String user1Token = "user-1";
        final String user2Token = "user-2";
        final List<String> users = Arrays.asList(user1Token, user2Token);
        final int amountOfVotesPerUser = 30;
        
        for (int votesRemain = amountOfVotesPerUser; votesRemain > 0; votesRemain--) {
        	users.parallelStream().forEach(user -> {
        		assertThat(
                        voteForTopic(user, topicIdToVote),
                        equalTo(HttpStatus.OK)
                );
        	});
        }
        
        assertThat(
                "user-1 can't vote anymore",
                voteForTopic(user1Token, topicIdToVote),
                equalTo(HttpStatus.METHOD_NOT_ALLOWED)
        );

        assertThat(
                "user-2 can't vote anymore",
                voteForTopic(user2Token, topicIdToVote),
                equalTo(HttpStatus.METHOD_NOT_ALLOWED)
        );
        
        assertThat(
                "Topic # 1 has 60 votes now (two users, each voted 30 times)",
                getTopic(1).getVotes(), equalTo(60)
        );
    }
    

    private HttpStatus voteForTopic(String accessToken, long topicId) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("access-token", accessToken);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(
                topicUrl,
                HttpMethod.POST,
                httpEntity,
                String.class,
                topicId
        ).getStatusCode();
    }

    private Topic getTopic(long topicId) {
        return restTemplate.getForEntity(topicUrl, Topic.class, topicId).getBody();
    }

}