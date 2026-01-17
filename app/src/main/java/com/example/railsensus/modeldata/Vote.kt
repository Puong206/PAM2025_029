package com.example.railsensus.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class VoteRequest(
    val tipe_vote: String
)

@Serializable
data class VoteResponse(
    val message: String,
    val trust_score: Int
)

data class DetailVote(
    val sensus_id: Int = 0,
    val tipe_vote: String = ""
)

data class UIVoteState(
    val voteDetail: DetailVote = DetailVote(),
    val isEntryValid: Boolean = false
)

fun DetailVote.toVoteRequest() = VoteRequest(
    tipe_vote = tipe_vote
)