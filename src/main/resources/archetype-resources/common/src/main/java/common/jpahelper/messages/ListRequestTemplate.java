package ${groupId}.common.jpahelper.messages;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class ListRequestTemplate {

    protected Integer page;

    protected Integer size;

    protected String sort;

    protected String direction;

    protected String search;

    public ListRequestTemplate(Integer page, Integer size, String sort, String direction, String search) {
        this.page = page;
        this.size = size;
        this.sort = sort;
        this.direction = direction;
        this.search = search;
    }


    public Pageable getPageRequest() {
        return PageRequest.of(page, size,
                Sort.Direction.valueOf(direction), sort);
    }

}
