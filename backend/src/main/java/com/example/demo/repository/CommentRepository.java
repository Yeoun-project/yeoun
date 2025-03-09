package com.example.demo.repository;

import com.example.demo.entity.CommentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Modifying
    @Query(value = "delete from comment where id = :commentId", nativeQuery = true)
    void delete(@Param("commentId") Long commentId);
}
