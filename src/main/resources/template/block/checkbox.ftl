<#--
/*
 * $Id: checkbox.ftl 720258 2008-11-24 19:05:16Z musachy $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
-->
<#--
NOTE: The 'header' stuff that follows is in this one file for checkbox due to the fact
that for checkboxes we do not want the label field to show up as checkboxes handle their own
lables
-->
<#assign hasFieldErrors = fieldErrors?? && fieldErrors[attributes.name]??/>
<div <#rt/><#if attributes.id??>id="wwgrp_${attributes.id}"<#rt/></#if> class="wwgrp">

<#if hasFieldErrors>
<div <#rt/><#if attributes.id??>id="wwerr_${attributes.id}"<#rt/></#if> class="wwerr">
<#list fieldErrors[attributes.name] as error>
    <div<#rt/>
    <#if attributes.id??>
     errorFor="${attributes.id}"<#rt/>
    </#if>
    class="errorMessage">
             ${error}
    </div><#t/>
</#list>
</div><#t/>
</#if>
<#if attributes.labelposition?default("") == 'left'>
<span <#rt/>
<#if attributes.id??>id="wwlbl_${attributes.id}"<#rt/></#if> class="wwlbl">
<label<#t/>
<#if attributes.id??>
 for="${attributes.id}"<#rt/>
</#if>
<#if hasFieldErrors>
 class="checkboxErrorLabel"<#rt/>
<#else>
 class="label"<#rt/>
</#if>
>${attributes.label}</label><#rt/>
</span>
</#if>

<#if attributes.labelposition?default("top") == 'top'>
<div <#rt/>
<#else>
<span <#rt/>
</#if>
<#if attributes.id??>id="wwctrl_${attributes.id}"<#rt/></#if> class="wwctrl">

<#if attributes.required?default(false)>
        <span class="required">*</span><#t/>
</#if>

<#include "/${attributes.templateDir}/simple/checkbox.ftl" />
<#if attributes.labelposition?default("") != 'left'>
<#if attributes.labelposition?default("top") == 'top'>
</div> <#rt/>
<#else>
</span>  <#rt/>
</#if>
<#if attributes.label??>
<#if attributes.labelposition?default("top") == 'top'>
<div <#rt/>
<#else>
<span <#rt/>
</#if>
<#if attributes.id??>id="wwlbl_${attributes.id}"<#rt/></#if> class="wwlbl">
<label<#t/>
<#if attributes.id??>
 for="${attributes.id}"<#rt/>
</#if>
<#if hasFieldErrors>
 class="checkboxErrorLabel"<#rt/>
<#else>
 class="checkboxLabel"<#rt/>
</#if>
>${attributes.label}</label><#rt/>
</#if>
</#if>
<#if attributes.label??>
<#if attributes.labelposition?default("top") == 'top'>
</div> <#rt/>
<#else>
</span> <#rt/>
</#if>
</#if>
</div>
