package com.blogapp.repositories;

import com.blogapp.entities.Category;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
//    List<Post> findByCategory(Category category);
    //Page<Post> findByPostId(Long id, Pageable pageable);
    Page<Post> findByCategory(Category category, Pageable pageable);

    List<Post> findByTitleContaining(String title);


    /*
* Using JPQL for search post by keyword
* @Query("select p from Post p where p.title like :key")
* List<Post> searchByTitle(@Param("key) String title);
* */
}
