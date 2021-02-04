package ${groupId}.common.jpahelper.messages;

public class ListRequestTemplateBuilder {
    private Integer page;
    private Integer size;
    private String sort;
    private String direction;
    private String search;

    public ListRequestTemplateBuilder setPage(Integer page) {
        this.page = page;
        return this;
    }

    public ListRequestTemplateBuilder setSize(Integer size) {
        this.size = size;
        return this;
    }

    public ListRequestTemplateBuilder setSort(String sort) {
        this.sort = sort;
        return this;
    }

    public ListRequestTemplateBuilder setDirection(String direction) {
        this.direction = direction;
        return this;
    }

    public ListRequestTemplateBuilder setSearch(String search) {
        this.search = search;
        return this;
    }

    public ListRequestTemplate createListRequestTemplate() {
        return new ListRequestTemplate(page, size, sort, direction, search);
    }
}