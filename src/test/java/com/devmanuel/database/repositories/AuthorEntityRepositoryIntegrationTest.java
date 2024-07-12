package com.devmanuel.database.repositories;

import com.devmanuel.database.TestDataUtil;
import com.devmanuel.database.domain.entities.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorEntityRepositoryIntegrationTest {

    private AuthorRepository underTest;

    @Autowired
    public AuthorEntityRepositoryIntegrationTest(AuthorRepository underTest){
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorEntityA();
        underTest.save(authorEntity);
        Optional<AuthorEntity> result = underTest.findById(authorEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled(){
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        underTest.save(authorEntityA);
        AuthorEntity authorEntityB = TestDataUtil.createTestAuthorB();
        underTest.save(authorEntityB);
        AuthorEntity authorEntityC = TestDataUtil.createTestAuthorC();
        underTest.save(authorEntityC);

        Iterable<AuthorEntity> result = underTest.findAll();
        assertThat(result).hasSize(3)
                .containsExactly(authorEntityA, authorEntityB, authorEntityC);
    }

//    @Test
//    public void testThatAuthorCanBeUpdated(){
//        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
//        underTest.save(authorEntityA);
//        authorEntityA.setName("UPDATED_NAME");
//        underTest.save(authorEntityA);
//        Optional<AuthorEntity> result = underTest.findById(authorEntityA.getId());
//        assertThat(result).isPresent();
//        assertThat(result.get()).isEqualTo(authorEntityA);
//    }

    @Test
    public void testThatAuthorCanBeDeleted(){
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorEntityA();
        underTest.save(authorEntityA);
        underTest.deleteById(authorEntityA.getId());
        Optional<AuthorEntity> result = underTest.findById(authorEntityA.getId());
        assertThat(result).isEmpty();
    }

    @Test
    public void testThatGetAuthorsWithAgeLessThan(){
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
        underTest.save(testAuthorEntityA);
        AuthorEntity testAuthorEntityB = TestDataUtil.createTestAuthorB();
        underTest.save(testAuthorEntityB);
        AuthorEntity testAuthorEntityC = TestDataUtil.createTestAuthorC();
        underTest.save(testAuthorEntityC);

        Iterable<AuthorEntity> result = underTest.ageLessThan(50);
        assertThat(result).containsExactly(testAuthorEntityB, testAuthorEntityC);
    }

//    @Test
//    public void testThatGetAuthorsWithAgeGreaterThan(){
//        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
//        underTest.save(testAuthorEntityA);
//        AuthorEntity testAuthorEntityB = TestDataUtil.createTestAuthorB();
//        underTest.save(testAuthorEntityB);
//        AuthorEntity testAuthorEntityC = TestDataUtil.createTestAuthorC();
//        underTest.save(testAuthorEntityC);
//
//        Iterable<AuthorEntity> result = underTest.findAuthorsWithAgeGreaterThan(20);
//        assertThat(result).contains(testAuthorEntityA, testAuthorEntityB);
//    }
}
