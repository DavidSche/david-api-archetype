package ${groupId}.restserver.domain

import org.springframework.data.annotation.Id

class Holding {
    @Id
    var id: String? = null
    var companyName: String? = null
}