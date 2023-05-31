package com.example.composeapp.domain.usecases

import com.example.composeapp.domain.models.Article
import com.example.composeapp.domain.repositories.ArticlesRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
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
class GetArticleByIdUseCaseTest {

    lateinit var useCase: GetArticleByIdUseCase

    @MockK
    lateinit var mockRepository: ArticlesRepository

    @Before
    fun setupData() {
        MockKAnnotations.init(this)
        useCase = GetArticleByIdUseCase(repository = mockRepository)
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
            mockRepository.getArticle(1)
        } returns mockArticle

        // Act
        runTest {
            val result = useCase.execute(1)

            // Assert
            assertEquals(expectedArticleData, result)
        }
    }

    @Test
    fun `Check use case throws HttpException if exception occurs in repository`() {
        // Arrange
        coEvery {
            mockRepository.getArticle(1)
        } throws HttpException(Response.error<ResponseBody>(401,
            "Unauthorized".toResponseBody("plain/text".toMediaType())
        ))
        // Act
        runTest {
            // Assert
            assertFailsWith<HttpException>("HTTP 401 Unauthorized") {
                useCase.execute(1)
            }
        }
    }
}