package com.prinpedia.backend.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Content {
    String label;
    List<Content> children;

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    public List<Content> getChildren() {
        return children;
    }
    public void setChildren(List<Content> children) {
        this.children = children;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("{label: ").append(label).append("\n");
        result.append("children: [\n");
        for(Content content : children) {
            result.append(content.toString());
            result.append(",");
        }
        result.append("]\n}");
        return result.toString();
    }
}
