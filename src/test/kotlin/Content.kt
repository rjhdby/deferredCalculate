data class Content(val id: String, val viewed: Boolean, val type: ContentType)

enum class ContentType {
    IMAGE, GIF, VIDEO
}