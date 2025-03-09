//package ru.netology

// Домашнее задание к занятию «2.2. ООП: объекты и классы»

data class Post(
    val id: Int,        // Идентификатор записи
    val ownerId: Int,  // Идентификатор владельца стены
    val fromId: Int,   // Идентификатор автора записи
    var text: String? = null,   // Текст записи
    val canPin:Boolean, // может ли текущий пользователь закрепить запись (1 — может, 0 — не может).
    val canDelete: Boolean,  // может ли текущий пользователь удалить запись (1 — может, 0 — не может).
    val canEdit: Boolean,   // может ли текущий пользователь редактировать запись (1 — может, 0 — не может).
    val markedAsAds: Boolean, // содержит ли запись отметку «реклама» (1 — да, 0 — нет).
    val likes: Likes,
    val attachments: MutableList<Attachment> = mutableListOf()  // Добавляем массив <Attachment>
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

// ---------------------
// класс Attachment
open class Attachment(
    val type: String // Тип вложения (Photo, Audio, Video, File, Sticker)
)

// Класс для вложения с фото
class PhotoAttachment(
    val photo: Photo // Данные о фото
) : Attachment("photo")

// Класс для вложения с аудио
class AudioAttachment(
    val audio: Audio // Данные об аудио
) : Attachment("audio")

// Класс для вложения с видео
class VideoAttachment(
    val video: Video // Данные о видео
) : Attachment("video")

// Класс для вложения с файлом
class FileAttachment(
    val file: File // Данные о файле
) : Attachment("file")

// Класс для вложения со стикером
class StickerAttachment(
    val sticker: Sticker // Данные о стикере
) : Attachment("sticker")
// ---------------------

// отдельные классы для хранения данных Photo, Audio, Video, File, Sticker
// ---------------------
// Данные о фото
data class Photo(
    val id: Int,
    var text: String
)

// Данные об аудио
data class Audio(
    val id: Int,
    val title: String
)

// Данные о видео
data class Video(
    val id: Int,
    val title: String
)

// Данные о файле
data class File(
    val id: Int,
    val title: String
)

// Данные о стикере
data class Sticker(
    val stickerId: Int,
    val isAllowed: Boolean
)
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

    // Проверка Задача №2. Attachments
    // ---------------------
    // Создаем объекты для вложений
    val photo = Photo(1, "Описание фотографии")
    val sticker = Sticker(2, true)

    // Создаем вложения
    val photoAttachment = PhotoAttachment(photo)
    val stickerAttachment = StickerAttachment(sticker)

    // Создаем пост с вложениями
    val post3 = Post(
        id = 333,
        ownerId = 3,
        fromId = 3,
        text = "Пост с вложениями",
        canPin = false,
        canDelete = true,
        canEdit = false,
        markedAsAds = true,
        likes = Likes(
            count = 30,
            userLikes = true,
            canLike = true,
            canPublish = true
        ),
        attachments = mutableListOf(photoAttachment, stickerAttachment)
    )

    // Выводим информацию о посте
    println("Пост ID: ${post3.id}")
    println("Текст: ${post3.text}")
    println("Вложения:")
    post3.attachments.forEach { attachment ->
        println("Тип: ${attachment.type}")
        when (attachment) {
            is PhotoAttachment -> println("ID фото: ${attachment.photo.id}")
            is StickerAttachment -> println("ID cтикера: ${attachment.sticker.stickerId}")
        }
    }
    // ---------------------
}