//package ru.netology

import org.junit.Test
import org.junit.Assert.*

class WallServiceTest {

    // Тест для функции add()
    // -------------------
    @Test
    fun add() {
        // Создаем объект WallService
        val wallService = WallService()

        // Создаем пост
        val post = Post(
            id = 0,
            ownerId = 1,
            fromId = 1,
            text = "Тестовый пост",
            canPin = true,
            canDelete = true,
            canEdit = true,
            markedAsAds = false,
            likes = Likes(
                count = 10,
                userLikes = true,
                canLike = true,
                canPublish = true
            )
        )

        // Добавляем пост
        val addedPost = wallService.add(post)

        // Проверяем тест
        assertEquals(0, addedPost.id)
    }
    // -------------------

    // Тест для функции update()
    // -------------------
    @Test
    // Проверяем, что пост обновлен
    fun update1() {
        // Создаем объект WallService
        val wallService = WallService()

        // Создаем и добавляем пост
        val post = Post(
            id = 0,
            ownerId = 1,
            fromId = 1,
            text = "Тестовый пост_2",
            canPin = true,
            canDelete = true,
            canEdit = true,
            markedAsAds = false,
            likes = Likes(
                count = 20,
                userLikes = true,
                canLike = true,
                canPublish = true
            )
        )
        wallService.add(post)

        // Создаем обновленный пост с тем же id
        val updatedPost = post.copy(text = "Обновленный текст")

        // Пытаемся обновить пост
        val result = wallService.update(updatedPost)

        // Проверяем, что пост обновлен
        assertTrue(result)
    }
    // -------------------

    // -------------------
    @Test
    // Проверяем, что пост не найден
    fun update2() {
        // Создаем объект WallService
        val wallService = WallService()

        // Создаем пост, но не добавляем его в WallService
        val post = Post(
            id = 21, // Несуществующий id
            ownerId = 1,
            fromId = 1,
            text = "Тестовый пост_3",
            canPin = true,
            canDelete = true,
            canEdit = true,
            markedAsAds = false,
            likes = Likes(
                count = 30,
                userLikes = true,
                canLike = true,
                canPublish = true
            )
        )

        // Пытаемся обновить несуществующий пост
        val result = wallService.update(post)

        // Проверяем, что пост не найден
        assertFalse(result)
    }
    // -------------------
}