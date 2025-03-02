//package ru.netology

// Домашнее задание к занятию «2.2. ООП: объекты и классы»

data class Post(
    val id: Int,        // Идентификатор записи
    val ownerId: Int,  // Идентификатор владельца стены
    val fromId: Int,   // Идентификатор автора записи
    var text:String,   // Текст записи
    val canPin:Boolean, // может ли текущий пользователь закрепить запись (1 — может, 0 — не может).
    val canDelete: Boolean,  // может ли текущий пользователь удалить запись (1 — может, 0 — не может).
    val canEdit: Boolean,   // может ли текущий пользователь редактировать запись (1 — может, 0 — не может).
    val markedAsAds: Boolean, // содержит ли запись отметку «реклама» (1 — да, 0 — нет).
    val likes: Likes
)

/*
likes
object
Информация о лайках к записи, объект с полями:
count (integer) — число пользователей, которым понравилась запись;
user_likes* (integer) — наличие отметки «Мне нравится» от текущего пользователя (1 — есть, 0 — нет);
can_like* (integer) — информация о том, может ли текущий пользователь поставить отметку «Мне нравится» (1 — может, 0 — не может);
can_publish* (integer) — информация о том, может ли текущий пользователь сделать репост записи (1 — может, 0 — не может).
*/
data class Likes (
    var count: Int,
    val userLikes: Boolean,
    val canLike: Boolean,
    val canPublish: Boolean) {
}

// ---------------------
class WallService {
    // Используем MutableList для хранения постов, так как он динамически расширяется
    private var posts = mutableListOf<Post>()
    private var countId = 0 // Счетчик id

    // Метод для добавления поста
    fun add(post: Post): Post {
        // Присваиваем посту уникальный id
        val newPost = post.copy(id = countId++)
        posts.add(newPost)
        return newPost
    }

    // Метод для обновления поста
    fun update(post: Post): Boolean {
        // Ищем пост с нужным id
        for ((index, existingPost) in posts.withIndex()) {
            if (existingPost.id == post.id) {
                // Обновляем пост
                posts[index] = post.copy()
                return true
            }
        }
        return false // Если пост не найден
    }
}
// ---------------------

fun main() {
    // Проверяем данные
    // ---------------------
    // Создаем объект WallService
    val wallService = WallService()

    // Создаем объект Likes
    val likes = Likes(10, true, true, true)

    // Создаем несколько постов
    val post1 = Post(
        id = 555,
        ownerId = 1,
        fromId = 1,
        text = "Первый пост",
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

    val post2 = Post(
        id = 888,
        ownerId = 2,
        fromId = 2,
        text = "Второй пост",
        canPin = false,
        canDelete = true,
        canEdit = false,
        markedAsAds = true,
        likes = Likes(
            count = 20,
            userLikes = true,
            canLike = true,
            canPublish = true
        )
    )

    // Добавляем посты в WallService
    val addedPost1 = wallService.add(post1)
    val addedPost2 = wallService.add(post2)

    println("Добавленные посты:")
    println(addedPost1)
    println(addedPost2)

    // Обновляем пост
    val newPost = addedPost1.copy(text = "Обновленный текст первого поста")
    println(newPost)
    // ---------------------

}