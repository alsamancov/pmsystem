package net.proselyte.pmsystem.model;

/**
 * Simple domain object that represents a Document
 *
 * @author Oleksii Samantsov
 */
public class Document extends NamedEntity {
    private String content;

    public Document() {
    }

    public Document(String name, String content) {
        super(name);
        this.content = content;
    }

    public Document(Long id, String name, String content) {
        super(name);
        this.setId(id);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Document{" +  super.toString() + '\'' +
                "content='" + content + '\'' +
                "} " ;
    }
}
