package join;

import org.apache.kafka.streams.kstream.ValueJoiner;
import topics.Posting;
import topics.PostingWithUser;
import topics.User;

public class PostingWithUserJoin implements ValueJoiner<Posting, User, PostingWithUser> {

    @Override
    public PostingWithUser apply(Posting posting, User user) {
        return PostingWithUser
                .builder()
                .id(posting.getId())
                .creaetTime(posting.getCreaetTime())
                .usuario(posting.getUsuario())
                .numberOfPosting(posting.getNumberOfPosting())
                .userName(user.getUserName())
                .create(user.getCreate())
                .update(user.getUpdate())
                .build();
    }
}
