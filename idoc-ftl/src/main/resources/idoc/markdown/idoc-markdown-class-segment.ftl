
<#if class??>
## ${class.name}--${class.commentFirstLine!"暂无说明"}

> <span style="color: #FF6666;">${class.remark!class.comment+" "!"暂无说明"}</span>

<#if class.methods??>
<#list class.methods as method>
###  ${method.name}-${method.commentFirstLine!"暂无说明"}

> <span style="color: coral; ">${method.remark!method.comment+" "!"暂无说明"}</span>

#### <span style="color: #CC6699; ">方法入参</span>

<#if method.params?? && (method.params?size > 0)>
| 字段 | 说明 | 字段类型 |
|:---|:---|:---|
<#list method.params as param>
|<span style="color: #EF6C00; ">${param.name}</span>| ${param.comment!""} | ${param.typeAlias!""} |
</#list>
<#else>
暂无入参
</#if>
##### 方法入参详情
<#if method.paramDetails?? && (method.paramDetails?size > 0)>
<#list method.paramDetails?keys as detailName>
- <span style="color: #0099CC;"> ${detailName} 入参详情</span>
| 字段 | 说明 | 字段类型 | 是否必填 | 备注 |
|:---|:---|:---|:---|:----|
<#list method.paramDetails[detailName] as param>
|<span style="color: #FF8F00;"> ${param.name}</span> | ${param.comment!""} | ${param.typeAlias!""} | ${param.require!""} | ${param.remark!""} |
</#list>
</#list>
<#else>
暂无入参详情
</#if>

#### <span style="color: #336699; ">方法出参</span>

<#if method.returns??>
| 说明 | 类型 |
|:---|:---|
| ${method.returns.comment!""} | ${method.returns.fullName!""} |
<#else>
暂无出参
</#if>

##### 方法出参详情

<#if method.returnDetails?? && (method.returnDetails?size > 0)>
<#list method.returnDetails?keys as detailName>
- <span style="color: #009999 ; ">${detailName} 出参详情</span>
| 字段 | 说明 | 字段类型 | 备注 |
|:---|:---|:---|:---|
<#list method.returnDetails[detailName] as param>
| <span style="color: #FF8F00 ; ">${param.name}</span> | ${param.comment!""} | ${param.typeAlias!""} | ${param.remark!""} |
</#list>
</#list>
<#else>
暂无出参详情
</#if>

----

</#list>
</#if>
</#if>





