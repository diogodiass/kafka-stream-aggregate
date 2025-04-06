package Processor;

import io.quarkus.kafka.client.serialization.ObjectMapperSerde;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import join.PostingWithUserJoin;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import topics.Posting;
import topics.PostingWithUser;
import topics.User;


@ApplicationScoped
public class KafkaStreamsProcessor {

    @Produces
    public Topology buildTopology() {
        StreamsBuilder builder = new StreamsBuilder();

        ObjectMapperSerde<Posting> postingSerde = new ObjectMapperSerde<>(Posting.class);
        ObjectMapperSerde<User> userSerde = new ObjectMapperSerde<>(User.class);
        ObjectMapperSerde<PostingWithUser> postingWithUserJoin = new ObjectMapperSerde<>(PostingWithUser.class);


        KStream<String, Posting> postings = builder.stream(
                "posting-topic",
                Consumed.with(Serdes.String(), postingSerde)
        );


        KTable<String, User> users = builder.table(
                "user-topic",
                Consumed.with(Serdes.String(), userSerde)
        );


        postings.join(
                        users,
                        new PostingWithUserJoin(),
                        Joined.with(Serdes.String(), postingSerde, userSerde))
                .to("posting-with-user-topic", Produced.with(Serdes.String(), postingWithUserJoin));

        return builder.build();
    }


}