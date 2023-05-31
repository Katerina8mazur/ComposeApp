package com.example.composeapp.domain.usecases

import com.example.composeapp.domain.models.Article
import com.example.composeapp.domain.models.ArticlesList
import com.example.composeapp.domain.repositories.ArticlesRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import kotlin.test.assertFailsWith


@OptIn(ExperimentalCoroutinesApi::class)
class GetAllArticlesUseCaseTest {

    lateinit var useCase: GetAllArticlesUseCase

    @MockK
    lateinit var mockRepository: ArticlesRepository

    @Before
    fun setupData() {
        MockKAnnotations.init(this)
        useCase = GetAllArticlesUseCase(repository = mockRepository)
    }

    @Test
    fun `Check the same data is returned from the usecase`() {
        // Arrange
        val mockArticle = mockk<Article> {
            every { id } returns 1
            every { title } returns "Title"
            every { url } returns "Url"
            every { imageUrl } returns "ImageUrl"
            every { newsSite } returns "NewsSite"
            every { summary } returns "Summary"
            every { publishedAt } returns "PublishedAt"
        }

        val mockAllArticles = mockk<ArticlesList> {
            every { count } returns 123
            every { next } returns "next"
            every { previous } returns "previous"
            every { results } returns persistentListOf(mockArticle)
        }

        val expectedAllArticlesData = ArticlesList(
            count = 123,
            next = "next",
            previous = "previous",
            results = persistentListOf(mockArticle)
        )
        coEvery {
            mockRepository.getAllArticles(5)
        } returns mockAllArticles

        // Act
        runTest {
            val result = useCase.execute(5)

            // Assert
            assertEquals(expectedAllArticlesData, result)
        }
    }

    @Test
    fun `Check use case throws HttpException if exception occurs in repository`() {
        // Arrange
        coEvery {
            mockRepository.getAllArticles(5)
        } throws HttpException(Response.error<ResponseBody>(401,
            "Unauthorized".toResponseBody("plain/text".toMediaType())
        ))
        // Act
        runTest {
            // Assert
            assertFailsWith<HttpException>("HTTP 401 Unauthorized") {
                useCase.execute(5)
            }
        }
    }
}