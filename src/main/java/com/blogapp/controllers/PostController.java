package com.blogapp.controllers;

import com.blogapp.config.AppConstants;
import com.blogapp.payloads.APIResponse;
import com.blogapp.payloads.PostDTO;
import com.blogapp.payloads.PostResponse;
import com.blogapp.services.FileService;
import com.blogapp.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    FileService fileService;

    @Value("${project.image}")
    private String imagePath;

    @Autowired
    ModelMapper modelMapper;

//    Create
    @PostMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO,
                                              @PathVariable Long userId,
                                              @PathVariable Long categoryId){
        return  ResponseEntity.ok(postService.createPost(postDTO, userId, categoryId));
    }
//    get post by user id
    @GetMapping("/user/{userId}")
    public  ResponseEntity<List<PostDTO>> getPostByUser(@PathVariable Long userId){
        List<PostDTO> postDTOS =  postService.getPostsByUser(userId);
        return ResponseEntity.ok(postDTOS);
    }
//    get post by category id
    @GetMapping("/category/{categoryId}")
    public  ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable Long categoryId,
                                                            @RequestParam(value = "pageNo", defaultValue = "0", required = false) Integer pageNo,
                                                            @RequestParam(value = "pageSize", defaultValue = "3", required = false) Integer pageSize
                                                            ){
        List<PostDTO> postDTOS =  postService.getPostsByCategory(categoryId, pageNo, pageSize);
        return ResponseEntity.ok(postDTOS);
    }
//    Get post by post id
    @GetMapping("/{postId}")
    public  ResponseEntity<PostDTO> getPostById(@PathVariable Long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }
//    Get all post
    @GetMapping("/getAll")
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NO, required = false) Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ){
        PostResponse postResponse =  postService.getAllPost(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(postResponse);
    }

//    Delete post
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.ok(new APIResponse("Post deleted Successfully", true));
    }

//    Update post
    @PutMapping("/update/{id}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Long id){
        return  ResponseEntity.ok(postService.updatePost(postDTO, id));
    }

//    Searching by post by keyword
    @GetMapping("/search/{keyword}")
    public  ResponseEntity<List<PostDTO>> getPostByKeyword(@PathVariable String keyword){
        List<PostDTO> postDTOS = postService.searchPosts(keyword);
        return  ResponseEntity.ok(postDTOS);
    }

    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImage(
            @RequestParam("image")MultipartFile multipartFile,
            @PathVariable Long postId
            ) throws IOException {
        PostDTO postDTO = postService.getPostById(postId);
        String uploadedImage =  fileService.uploadImage(imagePath, multipartFile);
        postDTO.setImageName(uploadedImage);
        PostDTO updatedPostDTO = postService.updatePost(postDTO, postId);
        return ResponseEntity.ok(updatedPostDTO);
    }

    @GetMapping(value = "/image/{postId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public  void downloadImage(@PathVariable Long postId, HttpServletResponse response) throws IOException{
        PostDTO postDTO = postService.getPostById(postId);
        String imageName = postDTO.getImageName();
        InputStream inputStream = fileService.getResource(imagePath, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream, response.getOutputStream());
    }

}
