/***
 * Copyright 2013 Teoti Graphix, LLC.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * 
 * @author Michael Schmalle <mschmalle@teotigraphix.com>
 */

package randori.compiler.internal.js.codegen.project.views.mediators;

import org.apache.flex.compiler.tree.as.IFunctionNode;
import org.junit.Test;

import randori.compiler.internal.js.codegen.project.RandoriTestProjectBase;

/**
 * @author Michael Schmalle
 */
public class TargetsMediatorTest extends RandoriTestProjectBase
{
    @Test
    public void test_constructor()
    {
        IFunctionNode node = findFunction("TargetsMediator", classNode);
        asBlockWalker.visitFunction(node);
        assertOut("mediators.TargetsMediator = function() {\n\tthis.targetList "
                + "= null;\n\tthis.service = null;\n\trandori.behaviors.AbstractMediator.call(this);\n}");
    }

    @Test
    public void test_onRegister()
    {
        IFunctionNode node = findFunction("onRegister", classNode);
        asBlockWalker.visitFunction(node);
        assertOut("mediators.TargetsMediator.prototype.onRegister = function() {"
                + "\n\tthis.service.get().then($createStaticDelegate(this, this.handleResult));\n}");
    }

    @Test
    public void test_handleResult()
    {
        IFunctionNode node = findFunction("handleResult", classNode);
        asBlockWalker.visitFunction(node);
        assertOut("mediators.TargetsMediator.prototype.handleResult = function(result) {"
                + "\n\tthis.targetList.set_data(result);\n}");
    }

    @Test
    public void test_file()
    {
        asBlockWalker.visitFile(fileNode);
    }

    protected String getBasePath()
    {
        return "C:\\Users\\Work\\Documents\\git\\RandoriAS\\DemoApplication\\src";
    }

    @Override
    protected String getTypeUnderTest()
    {
        return "mediators.TargetsMediator";
    }
}
