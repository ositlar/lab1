package auth

import UserInfo
import react.*
import react.dom.html.ReactHTML
import userInfoContext

fun ChildrenBuilder.authProvider(block: ChildrenBuilder.() -> Unit) =
    child(
        FC<PropsWithChildren>("AuthProvider") {
            var userInfo by useState<UserInfo>(null)
            CAuthContainer {
                user = userInfo?.first
                signIn = { userInfo = it }
                signOff = { userInfo = null }
            }
            if (userInfo == null)
                ReactHTML.h1 { +"Authentication is required" }
            else
                userInfoContext.Provider(userInfo) {
                    block()
                }
        }.create()
    )
