package com.example.composeapp.data.repositories

import com.example.composeapp.data.mappers.ArticleMapper
import com.example.composeapp.data.models.ArticleResponse
import com.example.composeapp.data.models.ArticlesListResponse
import com.example.composeapp.data.services.ArticlesApiService
import com.example.composeapp.domain.models.Article
import com.example.composeapp.domain.models.ArticlesList
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ArticlesRepositoryImplTest {

    lateinit var repository: ArticlesRepositoryImpl

    @MockK
    lateinit var mockService: ArticlesApiService
    private val articleMapper = ArticleMapper()

    @Before
    fun setupData() {
        MockKAnnotations.init(this)
        repository = ArticlesRepositoryImpl(
            service = mockService,
            mapper = articleMapper
        )
    }

    @Test
    fun `Check is article response correct maps into article model`() {
        // Arrange
        val mockArticleResponse = mockk<ArticleResponse> {
            every { id } returns 1
            every { title } returns "Title"
            every { url } returns "Url"
            every { imageUrl } returns "ImageUrl"
            every { newsSite } returns "NewsSite"
            every { summary } returns "Summary"
            every { publishedAt } returns "PublishedAt"
        }

        val expectedArticleData = Article(
            id = 1,
            title = "Title",
            url = "Url",
            imageUrl = "ImageUrl",
            newsSite = "NewsSite",
            summary = "Summary",
            publishedAt = "PublishedAt"
        )
        coEvery {
            mockService.getArticle(1)
        } returns mockArticleResponse

        // Act
        runTest {
            val result = repository.getArticle(1)

            // Assert
            assertEquals(expectedArticleData, result)
        }
    }

    @Test
    fun `Check is articles list response correct maps into articles list model`() {
        // Arrange
        val mockArticleResponse = mockk<ArticleResponse> {
            every { id } returns 1
            every { title } returns "Title"
            every { url } returns "Url"
            every { imageUrl } returns "ImageUrl"
            every { newsSite } returns "NewsSite"
            every { summary } returns "Summary"
            every { publishedAt } returns "PublishedAt"
        }

        val mockArticlesListResponse = mockk<ArticlesListResponse> {
            every { count } returns 123
            every { next } returns "next"
            every { previous } returns "previous"
            every { results } returns persistentListOf(mockArticleResponse)
        }

        val expectedArticleData = Article(
            id = 1,
            title = "Title",
            url = "Url",
            imageUrl = "ImageUrl",
            newsSite = "NewsSite",
            summary = "Summary",
            publishedAt = "PublishedAt"
        )
        val expectedAllArticlesData = ArticlesList(
            count = 123,
            next = "next",
            previous = "previous",
            results = persistentListOf(expectedArticleData)
        )
        coEvery {
            mockService.getAllArticles(5)
        } returns mockArticlesListResponse

        // Act
        runTest {
            val result = repository.getAllArticles(5)

            // Assert
            assertEquals(expectedAllArticlesData, result)
        }
    }
}