package com.github.houbb.idoc.common.handler.impl.simplify;

import com.github.houbb.idoc.api.model.metadata.DocField;
import com.github.houbb.idoc.api.model.metadata.DocMethod;
import com.github.houbb.idoc.api.model.metadata.DocMethodParameter;
import com.github.houbb.idoc.api.model.metadata.DocMethodReturn;
import com.github.houbb.idoc.common.handler.IHandler;
import com.github.houbb.idoc.common.model.SimplifyDocField;
import com.github.houbb.idoc.common.model.SimplifyDocMethod;
import com.github.houbb.idoc.common.model.SimplifyDocReturn;
import com.github.houbb.idoc.common.util.CollectionUtil;
import com.github.houbb.idoc.common.util.CommentUtil;
import com.github.houbb.idoc.common.util.ObjectUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 简化方法属性处理类
 *
 * @author binbin.hou
 * date 2019/2/15
 * @since 0.0.1
 */
public class SimplifyMethodHandler implements IHandler<DocMethod, SimplifyDocMethod> {
    @Override
    public SimplifyDocMethod handle(DocMethod docMethod) {
        if (null == docMethod) {
            return null;
        }
        SimplifyDocMethod commonDocMethod = new SimplifyDocMethod();
        commonDocMethod.setComment(docMethod.getComment());
        commonDocMethod.setRemark(docMethod.getRemark());
        commonDocMethod.setName(docMethod.getName());

        //v0.2.0 添加第一行备注，避免过多，导致格式错乱
        String commentFirstLine = CommentUtil.getFirstLine(commonDocMethod.getComment());
        commonDocMethod.setCommentFirstLine(commentFirstLine);
        // 处理入参
        final List<SimplifyDocField> params = buildParams(docMethod.getDocMethodParameterList());

        // 处理出参
        final SimplifyDocReturn simplifyDocReturn = buildReturn(docMethod.getDocMethodReturn());

        commonDocMethod.setParams(params);
        commonDocMethod.setReturns(simplifyDocReturn);
        commonDocMethod.setParamDetails(buildFieldDetails(params));
        // 出参详细
        List<SimplifyDocField> returnParams = buildReturnDetails(docMethod.getDocMethodReturn());
        commonDocMethod.setReturnDetails(buildFieldDetails(returnParams));

        return commonDocMethod;
    }

    /**
     * 构建出参信息
     *
     * @param docMethodReturn 返回值详情
     * @return 构建后的参数结果
     */
    private List<SimplifyDocField> buildReturnDetails(DocMethodReturn docMethodReturn) {
        if(docMethodReturn.getFullName()==null|| "".equals(docMethodReturn.getPackageName())
                || docMethodReturn.getPackageName().startsWith("java")){
            return null;
        }
        List<SimplifyDocField> resultList = new ArrayList<>();
        SimplifyDocField result = new SimplifyDocField();
        result.setType(docMethodReturn.getFullName());
        result.setName(docMethodReturn.getName());
        result.setRemark(docMethodReturn.getRemark());

        List<DocField> docFieldList = docMethodReturn.getDocFieldList();
        List<SimplifyDocField> list = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(docFieldList)) {
            for (DocField docField : docFieldList) {
                SimplifyDocField simplifyDocField = new SimplifyDocField();
                simplifyDocField.setType(docField.getType());
                simplifyDocField.setTypeAlias(docField.getTypeAlias());
                simplifyDocField.setCommentFirstLine(CommentUtil.getFirstLine(docField.getComment()));
                simplifyDocField.setRemark(docField.getRemark());
                simplifyDocField.setName(docField.getName());
                List<DocField> subDocFieldList = docField.getDocFieldList();
                if (CollectionUtil.isNotEmpty(subDocFieldList)) {
                    List<SimplifyDocField> simplifyDocFieldList = CollectionUtil.buildList(subDocFieldList,
                            new SimplifyDocFieldHandler());
                    simplifyDocField.setEntries(simplifyDocFieldList);
                }
                list.add(simplifyDocField);
            }
        }
        result.setEntries(list);
        resultList.add(result);
        return resultList;
    }

    /**
     * 构建入参信息
     *
     * @param docMethodParameterList 原始参数列表
     * @return 构建后的参数结果
     */
    private List<SimplifyDocField> buildParams(final List<DocMethodParameter> docMethodParameterList) {
        return CollectionUtil.buildList(docMethodParameterList, new SimplifyParamFieldHandler());
    }

    /**
     * 构建返回值结果
     *
     * @param docMethodReturn 返回结果
     * @return 构建后的参数列表
     */
    private SimplifyDocReturn buildReturn(final DocMethodReturn docMethodReturn) {
        if (ObjectUtil.isNull(docMethodReturn)) {
            return null;
        }
        if(docMethodReturn.getPackageName()==null){
            return null;
        }

        // 当前返回类的所有字段信息
        SimplifyDocReturn docReturn = new SimplifyDocReturn();

        docReturn.setName(docMethodReturn.getName());
        docReturn.setComment(docMethodReturn.getReturnComment());
        docReturn.setRemark(docMethodReturn.getRemark());
        docReturn.setFullName(docMethodReturn.getFullName());
        docReturn.setPackageName(docMethodReturn.getPackageName());
        return docReturn;
    }

    /**
     * 构建出参/入参字段明细
     *
     * @param fields 参数列表
     * @return 结果集合
     */
    private Map<String, List<SimplifyDocField>> buildFieldDetails(final List<SimplifyDocField> fields) {
        if (CollectionUtil.isEmpty(fields)) {
            return null;
        }

        final Map<String, List<SimplifyDocField>> map = new LinkedHashMap<>();

        for (SimplifyDocField docField : fields) {
            traversal(docField, map);
        }
        return map;
    }

    private void traversal(SimplifyDocField field, Map<String, List<SimplifyDocField>> map) {
        if (field.getEntries() == null) {
            return;
        }
        List<SimplifyDocField> entries = field.getEntries();
        map.put(field.getName(), entries);
        for (SimplifyDocField entry : entries) {
            traversal(entry, map);
        }
    }

}
