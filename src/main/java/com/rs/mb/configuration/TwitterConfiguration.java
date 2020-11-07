package com.rs.mb.configuration;

import com.github.twitch4j.TwitchClient;
import com.rs.mb.CredentialProperties;
import com.rs.mb.TwitterCredentialProperties;
import com.rs.mb.enums.TwitterUsersEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

@Configuration
public class TwitterConfiguration {

    @Autowired
    private TwitchClient twitchClient;

    @Value("${magicbot.owner}")
    private String owner;

    @Autowired
    private TwitterCredentialProperties twitterCredentialProperties;

    @Bean
    TwitterStream twitterStream() {
        TwitterStream twitterStream = new TwitterStreamFactory(
                new ConfigurationBuilder().setJSONStoreEnabled(true).build()).getInstance();

        twitterStream.setOAuthConsumer(twitterCredentialProperties.getOauth1(),
                twitterCredentialProperties.getOauth2());
        AccessToken token = new AccessToken(twitterCredentialProperties.getToken(),
                twitterCredentialProperties.getSecret());
        twitterStream.setOAuthAccessToken(token);

        StatusListener listener = new StatusListener() {
            //TODO: Use class mapper as orika to map the answer as java object
            public void onStatus(Status status) {

                String rawJSON = DataObjectFactory.getRawJSON(status);

                JSONObject jsonObj = new JSONObject(rawJSON);

                String tweet = jsonObj.getString("text");
                String username = jsonObj.getJSONObject("user").getString("screen_name");

                if (tweet.startsWith("RT") || tweet.startsWith(" RT")) {
                    return;
                }

                boolean isTweet = false;

                for (TwitterUsersEnum users : TwitterUsersEnum.values()) {
                    if (username.equalsIgnoreCase(users.getTwitter())) {
                        isTweet = true;
                        break;
                    }

                }

                if (isTweet == false) {
                    return;
                }

                if (isTweet) {

                    tweet = tweet.replaceAll("http.*?\\s", "");

                    twitchClient.getChat().sendMessage(owner,
                            "/me SeemsGood new tweet from " + username.toLowerCase() + ": " + tweet);
                }

            }

            @Override
            public void onException(Exception ex) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStallWarning(StallWarning warning) {
                // TODO Auto-generated method stub

            }
        };

        twitterStream.addListener(listener);

        FilterQuery query = new FilterQuery();
        long eu = 162198486l;
        long pajlada = 2189912750l;
        long forsen = 566662738l;
        long nymn = 3424517722l;
        long xqc = 785651770697523200l;

        query.follow(new long[] { eu, pajlada, forsen, nymn, xqc });
        twitterStream.filter(query);

        return twitterStream;
    }
}
