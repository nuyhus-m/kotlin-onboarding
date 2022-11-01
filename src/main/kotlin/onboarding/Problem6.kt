package onboarding

import org.mockito.internal.matchers.Null

fun solution6(forms: List<List<String>>): List<String> {

    val regularForms = checkEmailAndNickname(forms)

    val duplicateEmail = checkDuplicate(regularForms)

}

fun checkEmailAndNickname(forms: List<List<String>>): List<List<String>>{
    val regularForms = mutableListOf<List<String>>()
    for(i in forms.indices){
        if(forms[i][0].length in 11 until 20 && forms[i][0].contains("@email.com")
            && forms[i][0].length in 1 until 20 && isKorean(forms[i][1])){
            regularForms.add(forms[i])
        }
    }
    return regularForms
}

fun isKorean(nickname: String): Boolean {
    for(i in nickname.indices){
        val char = nickname.codePointAt(i)
        if (char < 0xAC00 || char > 0xD800)
            return false
    }
    return true
}

fun checkDuplicate(regularForms: List<List<String>>): MutableSet<String>{
    val duplicateNickname = mutableSetOf<String>()
    for(i in regularForms.indices) {
        for (char in 0 until regularForms[i][1].length-1){
            for (compare in i+1 until regularForms.size){
                val nickname = regularForms[i][1]
                val email = regularForms[i][0]
                val compareNickname = regularForms[compare][1]
                val compareEmail = regularForms[compare][0]

                if (compareNickname.contains(nickname.substring(char,char+2))){
                    duplicateNickname.add(email)
                    duplicateNickname.add(compareEmail)
                }
            }
        }
    }
    return duplicateNickname
}