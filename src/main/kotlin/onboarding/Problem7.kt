package onboarding

fun solution7(
    user: String,
    friends: List<List<String>>,
    visitors: List<String>
): List<String> {
    val userFriends = findUserFriends(user, friends)

    var score = getUserFriendsScore(user, friends, userFriends)
    score = getVisitorsScore(visitors, score, userFriends)

    return sortByScore(score)
}

fun findUserFriends(user: String,friends: List<List<String>>): MutableSet<String>{
    val userFriends = mutableSetOf<String>()
    friends.forEach{
        if(it[0] == user) {
            userFriends.add(it[1])
        } else if (it[1] == user) {
            userFriends.add(it[0])
        }
    }
    return userFriends
}

fun getUserFriendsScore(user: String, friends: List<List<String>>, userFriends: MutableSet<String>): MutableMap<String, Int>{
    val score = mutableMapOf<String, Int>()

    friends.forEach {
        if ((it[0] in userFriends) && (it[1] != user)) {
            score[it[1]] = score[it[1]]?.plus(10) ?: 10
        } else if ((it[1] in userFriends) && it[0] != user) {
            score[it[0]] = score[it[0]]?.plus(10) ?: 10
        }
    }

    return score
}

fun getVisitorsScore(visitors: List<String>, score: MutableMap<String, Int>, userFriends: MutableSet<String>): MutableMap<String, Int>{
    visitors.forEach {
        if(!userFriends.contains(it)){
            score[it] = score[it]?.plus(1) ?: 1
        }
    }
    return score
}

fun sortByScore(score: MutableMap<String, Int>): List<String>{
    val result = mutableListOf<Pair<String, Int>>()
    score.forEach {
        result.add(Pair(it.key, it.value))
    }

    result.sortWith { p1, p2 ->
        return@sortWith if (p1.second != p2.second) {
            -p1.second.compareTo(p2.second)
        } else {
            p1.first.compareTo(p2.first)
        }
    }

    return result.chunked(5)[0].map { it.first }
}