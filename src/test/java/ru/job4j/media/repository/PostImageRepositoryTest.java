package ru.job4j.media.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.media.model.PostImage;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostImageRepositoryTest {

    @Autowired
    private PostImageRepository postImageRepository;

    @BeforeEach
    public void setUp() {
        postImageRepository.deleteAll();
    }

    @Test
    public void whenSavePostImage_thenFindById() {
        var postImage = new PostImage();
        postImage.setPostId(1L);
        postImage.setUrl("https://i.pinimg.com/736x/e8/23/e0/e823e08eb26ea8cdb30f4174bebcebc7.jpg");
        postImageRepository.save(postImage);
        var foundPerson = postImageRepository.findById(postImage.getId());
        assertThat(foundPerson).isPresent();
    }

    @Test
    public void whenFindAll_thenReturnAllPostImages() {
        var postImage = new PostImage();
        postImage.setPostId(1L);
        postImage.setUrl("https://i.pinimg.com/736x/e8/23/e0/e823e08eb26ea8cdb30f4174bebcebc7.jpg");
        var postImage1 = new PostImage();
        postImage1.setPostId(2L);
        postImage1.setUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTO2kwHpH2yo2McHpmzONYTCIaPLbMYM5eWAA&s");
        postImageRepository.save(postImage);
        postImageRepository.save(postImage1);
        var postImages = postImageRepository.findAll();
        assertThat(postImages).hasSize(2);
        assertThat(postImages).extracting(PostImage::getUrl).contains("https://i.pinimg.com/736x/e8/23/e0/e823e08eb26ea8cdb30f4174bebcebc7.jpg"
                ,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTO2kwHpH2yo2McHpmzONYTCIaPLbMYM5eWAA&s");
    }

}