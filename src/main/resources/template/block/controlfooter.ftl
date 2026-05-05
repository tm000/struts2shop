<#--
/*
 * $Id: controlfooter.ftl 590812 2007-10-31 20:32:54Z apetrelli $
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
${attributes.after?if_exists}<#t/>
    <#lt/>
<#assign hasFieldErrors = attributes.name?? && fieldErrors?? && fieldErrors[attributes.name]??/>
<#if hasFieldErrors>
<ul <#rt/><#if attributes.id??>id="wwerr_${attributes.id}"<#rt/></#if> class="wwerr">
<#list fieldErrors[attributes.name] as error>
    <li<#rt/>
    <#if attributes.id??>
     errorFor="${attributes.id}"<#rt/>
    </#if>
    class="errorMessage">
             ${error}
    </li><#t/>
</#list>
</ul><#t/>
</#if>

<#if attributes.labelposition?default("top") == 'top'>
</div> <#rt/>
<#else>
</span> <#rt/>
</#if>
</tr>
</table>
</div>