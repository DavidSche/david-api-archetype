package ${groupId}.restserver.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Carrier {
    @Id
    var id: String? = null
    var displayName: String? = null
    var iataCodes: List<String>? = null
    var contact: Contact? = null
    var termAndConditions: TermAndConditions? = null
    var passengerAgeCategories: Map<PassengerCategory, AgeRange>? = null
}