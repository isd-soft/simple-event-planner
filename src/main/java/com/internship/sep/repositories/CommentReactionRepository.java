package com.internship.sep.repositories;
import com.internship.sep.models.CommentReaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReactionRepository extends JpaRepository<CommentReaction,Long> {
}
