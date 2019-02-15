package com.github.houbb.idoc.api.model.metadata;

import java.util.List;

/**
 * 字段信息
 * @author binbin.hou
 * @since 0.0.1
 */
public final class DocField extends BaseDoc {

    /**
     * 字段类型
     */
    private String type;

    /**
     * 是否需要
     */
    private String required;

    /**
     * 当前入参下面的字段信息
     */
    private List<DocField> docFieldList;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public List<DocField> getDocFieldList() {
        return docFieldList;
    }

    public void setDocFieldList(List<DocField> docFieldList) {
        this.docFieldList = docFieldList;
    }
}
