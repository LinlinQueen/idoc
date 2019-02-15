package com.github.houbb.idoc.common.handler.impl.simplify;

import com.github.houbb.idoc.api.model.metadata.DocClass;
import com.github.houbb.idoc.api.model.metadata.DocMethod;
import com.github.houbb.idoc.common.handler.IHandler;
import com.github.houbb.idoc.common.model.SimplifyDocClass;
import com.github.houbb.idoc.common.model.SimplifyDocMethod;
import com.github.houbb.idoc.common.util.CollectionUtil;

import java.util.List;

/**
 * 通用类转换工具
 * @author binbin.hou
 * @since 0.0.1
 */
public class SimplifyClassHandler implements IHandler<DocClass, SimplifyDocClass> {
    @Override
    public SimplifyDocClass handle(DocClass docClass) {
        SimplifyDocClass commonDocClass = new SimplifyDocClass();
        commonDocClass.setName(docClass.getName());
        commonDocClass.setComment(docClass.getComment());
        commonDocClass.setRemark(docClass.getRemark());

        // 方法信息
        List<DocMethod> docMethodList = docClass.getDocMethodList();
        List<SimplifyDocMethod> commonDocMethodList = CollectionUtil.buildList(docMethodList, new SimplifyMethodHandler());
        commonDocClass.setMethods(commonDocMethodList);

        return commonDocClass;
    }

}
