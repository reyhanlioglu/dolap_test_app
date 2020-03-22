package com.example.dolaptestapp.product_detail.model


import com.google.gson.annotations.SerializedName



data class SocialDetails(
    @SerializedName("likeCount")
    val likeCount: Int?,

    @SerializedName("commentCounts")
    val commentCounts: CommentCounts?
)


data class CommentCounts(
    @SerializedName("averageRating")
    var averageRating: Float?,

    @SerializedName("anonymousCommentsCount")
    var anonymousCommentsCount: Int?,

    @SerializedName("memberCommentsCount")
    var memberCommentsCount: Int?
)




