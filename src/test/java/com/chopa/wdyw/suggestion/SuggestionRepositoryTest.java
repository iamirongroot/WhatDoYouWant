package com.chopa.wdyw.suggestion;

import java.util.Arrays;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.chopa.wdyw.category.repository.CategoryRepository;
import com.chopa.wdyw.category.model.Category;
import com.chopa.wdyw.suggestion.model.Suggestion;
import com.chopa.wdyw.suggestion.repository.SuggestionRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("local")
@TestPropertySource(properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class SuggestionRepositoryTest {

	@Autowired
	private SuggestionRepository suggestionRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private EntityManager entityManager;

	@BeforeEach
	public void init() {
		Category category = new Category();
		category.setName("카테고리");
		categoryRepository.save(category);

		Suggestion newSuggestion = new Suggestion();
		newSuggestion.setCategoryList(Arrays.asList(category));
		newSuggestion.setContent("내용");
		newSuggestion.setTitle("타이틀");
		suggestionRepository.save(newSuggestion);

		entityManager.flush();
		entityManager.clear();
	}

	@Test
	void getOneTest() {
		Suggestion suggestion = suggestionRepository.getOne(1L);
		Assertions.assertEquals(1, suggestion.getCategoryList().size());
	}
}
