package movie.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MovieRepository {
    private final MongoTemplate mongoTemplate;

    @Autowired
    public MovieRepository(final MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public movie.domain.Movie update(movie.domain.Movie movie) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(movie.getTitle()));
        query.addCriteria(Criteria.where("cast").is(movie.getCast()));
        query.addCriteria(Criteria.where("year").is(movie.getYear()));
        FindAndReplaceOptions options = new FindAndReplaceOptions().upsert().returnNew();
        return mongoTemplate.findAndReplace(query, movie, options, movie.domain.Movie.class, "movies", movie.domain.Movie.class);
    }
}